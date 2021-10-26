package com.alphitardian.onboardingproject.data.user.data_source.local

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

interface LocalDataSource {
    suspend fun getNews() : List<NewsEntity>
    fun insertNews(news: NewsEntity)
    suspend fun getUserProfile() : UserEntity?
    fun insertProfile(user: UserEntity)
}