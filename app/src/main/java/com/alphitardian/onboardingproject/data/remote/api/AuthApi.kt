package com.alphitardian.onboardingproject.data.remote.api

import com.alphitardian.onboardingproject.data.remote.entity.auth.LoginRequest
import com.alphitardian.onboardingproject.data.remote.entity.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun loginUser(@Body requestBody: LoginRequest): TokenResponse

    @GET("auth/token")
    suspend fun getUserToken(@Header("Authorization") userToken: String): TokenResponse
}