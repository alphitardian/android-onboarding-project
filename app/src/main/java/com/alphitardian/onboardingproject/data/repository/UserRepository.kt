package com.alphitardian.onboardingproject.data.repository

import com.alphitardian.onboardingproject.data.remote.entity.news.NewsResponse
import com.alphitardian.onboardingproject.data.remote.entity.user.UserResponse

interface UserRepository {
    suspend fun getUserProfile(userToken: String): UserResponse
    suspend fun getNews(userToken: String): NewsResponse
}