package com.alphitardian.onboardingproject.presentation.home

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.ChannelResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsItemResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.decrypt_token.DecryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_news.GetNewsUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_profile.GetProfileUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileUseCase: GetProfileUseCase,
    private val newsUseCase: GetNewsUseCase,
    private val tokenUseCase: GetTokenUseCase,
    private val encryptTokenUseCase: EncryptTokenUseCase,
    private val decryptTokenUseCase: DecryptTokenUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {
    var isLoggedin = mutableStateOf(true)
    var userDecryptedToken = mutableStateOf<String?>("")

    private var mutableProfile: MutableLiveData<Resource<UserEntity>> = MutableLiveData()
    val profile: LiveData<Resource<UserEntity>> get() = mutableProfile

    private var mutableNews: MutableLiveData<Resource<List<NewsEntity>>> = MutableLiveData()
    val news: LiveData<Resource<List<NewsEntity>>> get() = mutableNews

    private var mutableRefreshToken: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val refreshToken: LiveData<Resource<TokenResponse>> get() = mutableRefreshToken

    private val datastore = PrefStore(context)
    private val HOUR_IN_EPOCH_SECONDS = 3600

    init {
        viewModelScope.launch {
            delay(1000) // to able to get data from datastore

            val userToken = datastore.userToken.first().toString()
            val userTokenIV = datastore.tokenInitializationVector.first().toString()
            userDecryptedToken.value = decryptTokenUseCase(userToken, userTokenIV)
            checkUserLoginTime()
            userDecryptedToken.value?.let {
                getUserProfile(it)
                getUserNews(it)
            }
        }
    }

    private fun checkUserLoginTime() {
        viewModelScope.launch {
            val expiredTime = datastore.userExpired.first()

            if (expiredTime >= 0) {
                val endTime = expiredTime - Date().toInstant().epochSecond

                when {
                    endTime > HOUR_IN_EPOCH_SECONDS -> isLoggedin.value = true
                    endTime in 0 until HOUR_IN_EPOCH_SECONDS -> {
                        userDecryptedToken.value?.let {
                            getNewToken(it)
                        }
                        isLoggedin.value = true
                    }
                    else -> isLoggedin.value = false
                }
            }
        }
    }

    fun getUserProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                mutableProfile.postValue(Resource.Loading())
                val result = profileUseCase(token)
                result?.let { mutableProfile.postValue(Resource.Success<UserEntity>(data = it)) }
            }.getOrElse {
                val errorCode = handleErrorCode(it)
                val error = Resource.Error<UserEntity>(error = it, code = errorCode)
                mutableProfile.postValue(error)
            }
        }
    }

    fun getUserNews(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                mutableNews.postValue(Resource.Loading())
                val result = newsUseCase(token)
                result?.let { mutableNews.postValue(Resource.Success<List<NewsEntity>>(data = it)) }
            }.getOrElse {
                val errorCode = handleErrorCode(it)
                val error = Resource.Error<List<NewsEntity>>(error = it, code = errorCode)
                mutableNews.postValue(error)
            }
        }
    }

    fun getNewToken(token: String) {
        viewModelScope.launch {
            runCatching {
                mutableRefreshToken.postValue(Resource.Loading())
                val result = tokenUseCase(token)
                mutableRefreshToken.postValue(Resource.Success<TokenResponse>(data = result))
                dataStoreTransaction(result)
            }.getOrElse {
                val errorCode = handleErrorCode(it)
                val error = Resource.Error<TokenResponse>(error = it, errorCode)
                mutableRefreshToken.postValue(error)
                isLoggedin.value = false
            }
        }
    }

    private fun handleErrorCode(error: Throwable): Int {
        var code: Int = 0
        if (error is HttpException) {
            val errorMessage = error.localizedMessage
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(Integer.parseInt(errorCode))) {
                ErrorState.ERROR_401 -> code = errorCode.toInt()
                ErrorState.ERROR_UNKNOWN -> code = 0
            }
        } else {
            code = 0
        }
        return code
    }

    fun formatNewsCategory(channel: String?): String? {
        val validCategory = channel?.split("/")?.get(1)?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
        return validCategory
    }

    fun formatNewsDate(date: String?): String? {
        val localDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yy")
        return formatter.format(localDate)
    }

    private fun dataStoreTransaction(response: TokenResponse) {
        val time = response.expiresTime
        val localDate = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
        val epochTime = localDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli() / 1000
        saveExpiredTime(epochTime)
        encryptToken(response.token)
    }

    private fun encryptToken(token: String) {
        viewModelScope.launch {
            val encryptedMap = encryptTokenUseCase(token)
            encryptedMap["token"]?.let { saveToken(it) }
            encryptedMap["iv"]?.let { saveTokenIV(it) }
        }
    }

    private fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveToken(token)
        }
    }

    private fun saveTokenIV(iv: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveTokenInitializationVector(iv)
        }
    }

    private fun saveExpiredTime(time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveExpiredTime(time)
        }
    }
}