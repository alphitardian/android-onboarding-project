package com.alphitardian.onboardingproject.data.remote

import com.alphitardian.onboardingproject.data.remote.entity.LoginRequest
import com.alphitardian.onboardingproject.data.remote.entity.NewsResponse
import com.alphitardian.onboardingproject.data.remote.entity.TokenResponse
import com.alphitardian.onboardingproject.data.remote.entity.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NewsApi {
    @POST("auth/login")
    suspend fun loginUser(@Body requestBody: LoginRequest): TokenResponse

    @GET("auth/token")
    suspend fun getUserToken(@Header("Authorization") userToken: String): TokenResponse

    @GET("me/profile")
    suspend fun getUserProfile(@Header("Authorization") userToken: String): UserResponse

    @GET("me/news")
    suspend fun getNews(@Header("Authorization") userToken: String): NewsResponse
}