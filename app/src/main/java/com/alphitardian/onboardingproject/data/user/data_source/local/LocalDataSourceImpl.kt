package com.alphitardian.onboardingproject.data.user.data_source.local

import com.alphitardian.onboardingproject.data.user.data_source.local.database.NewsDao
import com.alphitardian.onboardingproject.data.user.data_source.local.database.UserDao
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val userDao: UserDao,
) : LocalDataSource {
    override suspend fun getNews(): List<NewsEntity> = newsDao.getNews()

    override fun insertNews(news: NewsEntity) = newsDao.insertNews(news)

    override suspend fun getUserProfile(): UserEntity = userDao.getUserProfile()

    override fun insertProfile(user: UserEntity) = userDao.insertProfile(user)
}