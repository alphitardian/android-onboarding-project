package com.alphitardian.onboardingproject.data.user.repository

import com.alphitardian.onboardingproject.domain.repository.UserRepository
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import retrofit2.Response

class UserRepositoryImpl(private val api: UserApi) : UserRepository {
    override suspend fun getUserProfile(userToken: String): Response<UserResponse> {
        return api.getUserProfile(userToken)
    }

    override suspend fun getNews(userToken: String): Response<NewsResponse> {
        return api.getNews(userToken)
    }
}