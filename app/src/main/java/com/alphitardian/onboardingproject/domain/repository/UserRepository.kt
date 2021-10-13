package com.alphitardian.onboardingproject.domain.repository

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUserProfile(userToken: String): LiveData<Resource<UserResponse>>
    suspend fun getNews(userToken: String): LiveData<Resource<NewsResponse>>
}