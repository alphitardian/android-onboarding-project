package com.alphitardian.onboardingproject.data.user

import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSource
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

class FakeLocalDataSource : LocalDataSource {

    val mockNewsData = mutableListOf<NewsEntity>()
    val mockUserData = mutableListOf<UserEntity>()

    override suspend fun getNews(): List<NewsEntity> = mockNewsData

    override fun insertNews(news: NewsEntity) {
        mockNewsData.add(news)
    }

    override suspend fun getUserProfile(): UserEntity? = mockUserData[0]

    override fun insertProfile(user: UserEntity) {
        mockUserData.add(user)
    }
}