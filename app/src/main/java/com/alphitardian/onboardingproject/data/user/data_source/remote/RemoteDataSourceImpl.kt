package com.alphitardian.onboardingproject.data.user.data_source.remote

import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : RemoteDataSource {
    override suspend fun getProfile(): UserResponse {
        return userApi.getUserProfile()
    }

    override suspend fun getNews(): NewsResponse {
        return userApi.getNews()
    }
}