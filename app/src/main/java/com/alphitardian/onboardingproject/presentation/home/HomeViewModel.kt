package com.alphitardian.onboardingproject.presentation.home

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.ChannelResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsItemResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.get_news.GetNewsUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_profile.GetProfileUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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

    private val datastore = PrefStore(context)
    private val HOUR_IN_EPOCH_SECONDS = 3600

    init {
        viewModelScope.launch {
            checkUserLoginTime()

            val userToken = datastore.userToken.first().toString()
            val userTokenIV = datastore.tokenInitializationVector.first().toString()
            userDecryptedToken.value = KeystoreHelper.decrypt(userToken, userTokenIV)
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

                isLoggedin.value = endTime > HOUR_IN_EPOCH_SECONDS
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
            }
        }
    }

    fun getUserNews(token: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                mutableNews.postValue(Resource.Loading())
                val result = newsUseCase(token)
                mutableNews.postValue(Resource.Success<List<NewsItemResponse>>(data = result.data))
            }.getOrElse {
                val error = Resource.Error<List<NewsItemResponse>>(error = it)
                mutableNews.postValue(error)
            }
        }
    }

    fun formatNewsCategory(channel: ChannelResponse?) : String? {
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
}