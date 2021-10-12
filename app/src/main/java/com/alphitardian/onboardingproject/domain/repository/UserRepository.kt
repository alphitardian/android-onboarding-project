package com.alphitardian.onboardingproject.domain.repository

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUserProfile(userToken: String): Response<UserResponse>
    suspend fun getNews(userToken: String): Response<NewsResponse>
}