package com.alphitardian.onboardingproject.data.user.data_source.remote

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : RemoteDataSource {
    override suspend fun getProfile(userToken: String): Resource<UserResponse> {
        return try {
            val response = userApi.getUserProfile(userToken)
            Resource.Success<UserResponse>(data = response)
        } catch (error: Throwable) {
            Resource.Error<UserResponse>(error = error)
        }
    }

    override suspend fun getNews(userToken: String): Resource<NewsResponse> {
        return try {
            val response = userApi.getNews(userToken)
            Resource.Success<NewsResponse>(data = response)
        } catch (error: Throwable) {
            Resource.Error<NewsResponse>(error = error)
        }
    }
}