package com.alphitardian.onboardingproject.data.user.data_source.remote.network

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("me/profile")
    suspend fun getUserProfile(@Header("Authorization") userToken: String): Response<UserResponse>

    @GET("me/news")
    suspend fun getNews(@Header("Authorization") userToken: String): Response<NewsResponse>
}