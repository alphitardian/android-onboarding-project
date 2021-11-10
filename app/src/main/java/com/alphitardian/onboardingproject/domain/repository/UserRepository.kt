package com.alphitardian.onboardingproject.domain.repository

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

interface UserRepository {
    suspend fun getUserProfile(): UserEntity?
    suspend fun getNews(): List<NewsEntity>?
}