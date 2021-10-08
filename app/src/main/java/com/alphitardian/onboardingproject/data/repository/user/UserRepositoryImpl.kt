package com.alphitardian.onboardingproject.data.repository.user

import com.alphitardian.onboardingproject.data.remote.api.UserApi
import com.alphitardian.onboardingproject.data.remote.entity.news.NewsResponse
import com.alphitardian.onboardingproject.data.remote.entity.user.UserResponse

class UserRepositoryImpl(private val api: UserApi) : UserRepository {
    override suspend fun getUserProfile(userToken: String): UserResponse {
        return api.getUserProfile(userToken)
    }

    override suspend fun getNews(userToken: String): NewsResponse {
        return api.getNews(userToken)
    }
}