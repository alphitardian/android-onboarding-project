package com.alphitardian.onboardingproject.presentation.home

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.ChannelResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsItemResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
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
    @ApplicationContext context: Context,
) : ViewModel() {
    var isLoggedin = mutableStateOf(true)
    var userDecryptedToken = mutableStateOf<ByteArray?>(byteArrayOf())

    private var mutableProfile: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val profile: LiveData<Resource<UserResponse>> get() = mutableProfile

    private var mutableNews: MutableLiveData<Resource<List<NewsItemResponse>>> = MutableLiveData()
    val news: LiveData<Resource<List<NewsItemResponse>>> get() = mutableNews

    private var mutableRefreshToken: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val refreshToken: LiveData<Resource<TokenResponse>> get() = mutableRefreshToken

    var mutableErrorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = mutableErrorState

    private val datastore = PrefStore(context)
    private val HOUR_IN_EPOCH_SECONDS = 3600

    init {
        viewModelScope.launch {
            delay(1000) // to able to get data from datastore

            val userToken = datastore.userToken.first().toString()
            val userTokenIV = datastore.tokenInitializationVector.first().toString()
            userDecryptedToken.value = KeystoreHelper.decrypt(userToken, userTokenIV)
            checkUserLoginTime()
            userDecryptedToken.value?.decodeToString()?.let {
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
                        userDecryptedToken.value?.decodeToString()?.let {
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
        viewModelScope.launch {
            runCatching {
                mutableProfile.postValue(Resource.Loading())
                val result = profileUseCase(token)
                mutableProfile.postValue(Resource.Success<UserResponse>(data = result))
            }.getOrElse {
                val error = Resource.Error<UserResponse>(error = it)
                mutableProfile.postValue(error)
                handleError(error)
            }
        }
    }

    fun getUserNews(token: String) {
        viewModelScope.launch {
            runCatching {
                mutableNews.postValue(Resource.Loading())
                val result = newsUseCase(token)
                mutableNews.postValue(Resource.Success<List<NewsItemResponse>>(data = result.data))
            }.getOrElse {
                val error = Resource.Error<List<NewsItemResponse>>(error = it)
                mutableNews.postValue(error)
                handleError(error)
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
                val error = Resource.Error<TokenResponse>(error = it)
                mutableRefreshToken.postValue(error)
                handleError(error)
                isLoggedin.value = false
            }
        }
    }

    private fun handleError(response: Resource.Error<*>) {
        if (response.error is HttpException) {
            val errorMessage = response.error.localizedMessage
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(Integer.parseInt(errorCode))) {
                ErrorState.ERROR_401 -> mutableErrorState.value = ErrorState.ERROR_401.code
                ErrorState.ERROR_UNKNOWN -> mutableErrorState.value = ErrorState.ERROR_UNKNOWN.code
            }
        } else {
            mutableErrorState.value = ErrorState.ERROR_UNKNOWN.code
        }
    }

    fun formatNewsCategory(channel: ChannelResponse?): String? {
        val validCategory = channel?.name?.split("/")?.get(1)?.replaceFirstChar {
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
            val encrypt = KeystoreHelper.encrypt(token.toByteArray())
            val encryptedToken = Base64.encodeToString(encrypt["encrypted"], Base64.DEFAULT)
            val iv = Base64.encodeToString(encrypt["iv"], Base64.DEFAULT)

            saveToken(encryptedToken)
            saveTokenIV(iv)
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