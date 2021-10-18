package com.alphitardian.onboardingproject.data.user.data_source.local

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.data.user.data_source.local.database.NewsDao
import com.alphitardian.onboardingproject.data.user.data_source.local.database.UserDao
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val newsDao: NewsDao,
    private val userDao: UserDao,
) {
    fun getNews(): LiveData<List<NewsEntity>> = newsDao.getNews()

    fun insertNews(news: NewsEntity) = newsDao.insertNews(news)

    fun getUserProfile(username: String): LiveData<UserEntity> = userDao.getUserProfile(username)

    fun insertProfile(user: UserEntity) = userDao.insertProfile(user)
}