package com.alphitardian.onboardingproject.data.auth.data_source.remote.network

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
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