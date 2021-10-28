package com.alphitardian.onboardingproject.domain.repository

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse

interface UserRepository {
    suspend fun getUserProfile(userToken: String): UserEntity?
    suspend fun getNews(userToken: String): List<NewsEntity>?
}