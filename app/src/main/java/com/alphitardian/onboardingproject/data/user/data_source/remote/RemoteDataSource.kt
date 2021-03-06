package com.alphitardian.onboardingproject.data.user.data_source.remote

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse

interface RemoteDataSource {
    suspend fun getProfile(): UserResponse
    suspend fun getNews(): NewsResponse
}