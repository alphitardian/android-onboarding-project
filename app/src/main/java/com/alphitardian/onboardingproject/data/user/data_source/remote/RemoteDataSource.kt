package com.alphitardian.onboardingproject.data.user.data_source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse

class RemoteDataSource(private val userApi: UserApi) {
    suspend fun getProfile(userToken: String): LiveData<Resource<UserResponse>> {
        val result = MutableLiveData<Resource<UserResponse>>()

        try {
            val response = userApi.getUserProfile(userToken)
            result.postValue(Resource.Success<UserResponse>(data = response))
        } catch (error: Throwable) {
            result.postValue(Resource.Error<UserResponse>(error = error))
        }

        return result
    }

    suspend fun getNews(userToken: String): LiveData<Resource<NewsResponse>> {
        val result = MutableLiveData<Resource<NewsResponse>>()

        try {
            val response = userApi.getNews(userToken)
            result.postValue(Resource.Success<NewsResponse>(data = response))
        } catch (error: Throwable) {
            result.postValue(Resource.Error<NewsResponse>(error = error))
        }

        return result
    }
}