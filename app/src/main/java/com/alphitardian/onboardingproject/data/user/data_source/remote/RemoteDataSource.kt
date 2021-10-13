package com.alphitardian.onboardingproject.data.user.data_source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse

class RemoteDataSource(private val userApi: UserApi) {
    suspend fun getProfile(userToken: String): LiveData<Resource<UserResponse>> {
        val result = MutableLiveData<Resource<UserResponse>>()
        val response = userApi.getUserProfile(userToken)

        when {
            response.isSuccessful -> {
                result.postValue(response.body()
                    ?.let { Resource.Success<UserResponse>(data = it) })
            }
            else -> {
                result.postValue(Resource.Error(state = ErrorState.fromErrorCode(response.code())))
            }
        }

        return result
    }

    suspend fun getNews(userToken: String): LiveData<Resource<NewsResponse>> {
        val result = MutableLiveData<Resource<NewsResponse>>()
        val response = userApi.getNews(userToken)

        when {
            response.isSuccessful -> {
                result.postValue(response.body()
                    ?.let { Resource.Success<NewsResponse>(data = it) })
            }
            else -> {
                result.postValue(Resource.Error(state = ErrorState.fromErrorCode(response.code())))
            }
        }

        return result
    }
}