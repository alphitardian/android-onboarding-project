package com.alphitardian.onboardingproject.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.common.Extension.handleErrorCode
import com.alphitardian.onboardingproject.common.Extension.toEpochTime
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.check_user_login_time.CheckUserLoginTimeUseCase
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_news.GetNewsUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_profile.GetProfileUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileUseCase: GetProfileUseCase,
    private val newsUseCase: GetNewsUseCase,
    private val tokenUseCase: GetTokenUseCase,
    private val encryptTokenUseCase: EncryptTokenUseCase,
    private val checkUserLoginTimeUseCase: CheckUserLoginTimeUseCase,
    private val datastore: PrefStore,
) : ViewModel() {
    var isLoggedin = mutableStateOf(true)

    private var mutableProfile: MutableLiveData<Resource<UserEntity>> = MutableLiveData()
    val profile: LiveData<Resource<UserEntity>> get() = mutableProfile

    private var mutableNews: MutableLiveData<Resource<List<NewsEntity>>> = MutableLiveData()
    val news: LiveData<Resource<List<NewsEntity>>> get() = mutableNews

    private var mutableRefreshToken: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val refreshToken: LiveData<Resource<TokenResponse>> get() = mutableRefreshToken

    init {
        viewModelScope.launch {
            getUserProfile()
            getUserNews()

            isLoggedin.value = checkUserLoginTimeUseCase { getNewToken() }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            EspressoIdlingResource.increment()
            runCatching {
                mutableProfile.postValue(Resource.Loading())
                val result = profileUseCase()
                result?.let { mutableProfile.postValue(Resource.Success<UserEntity>(data = it)) }
            }.getOrElse {
                val error = Resource.Error<UserEntity>(error = it, code = it.handleErrorCode())
                mutableProfile.postValue(error)
            }
            EspressoIdlingResource.decrement()
        }
    }

    fun getUserNews() {
        viewModelScope.launch(Dispatchers.IO) {
            EspressoIdlingResource.increment()
            runCatching {
                mutableNews.postValue(Resource.Loading())
                val result = newsUseCase()
                result?.let { mutableNews.postValue(Resource.Success<List<NewsEntity>>(data = it)) }
            }.getOrElse {
                val error =
                    Resource.Error<List<NewsEntity>>(error = it, code = it.handleErrorCode())
                mutableNews.postValue(error)
            }
            EspressoIdlingResource.decrement()
        }
    }

    fun getNewToken() {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            runCatching {
                mutableRefreshToken.postValue(Resource.Loading())
                val result = tokenUseCase()
                mutableRefreshToken.postValue(Resource.Success<TokenResponse>(data = result))
                dataStoreTransaction(result)
            }.getOrElse {
                val error =
                    Resource.Error<TokenResponse>(error = it, code = it.handleErrorCode())
                mutableRefreshToken.postValue(error)
                isLoggedin.value = false
            }
            EspressoIdlingResource.decrement()
        }
    }

    private fun dataStoreTransaction(response: TokenResponse) {
        val time = response.expiresTime.toEpochTime()
        saveExpiredTime(time)
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