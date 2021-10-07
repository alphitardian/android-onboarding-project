package com.alphitardian.onboardingproject.data.remote.api

import com.alphitardian.onboardingproject.data.remote.entity.news.NewsResponse
import com.alphitardian.onboardingproject.data.remote.entity.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("me/profile")
    suspend fun getUserProfile(@Header("Authorization") userToken: String): UserResponse

    @GET("me/news")
    suspend fun getNews(@Header("Authorization") userToken: String): NewsResponse
}