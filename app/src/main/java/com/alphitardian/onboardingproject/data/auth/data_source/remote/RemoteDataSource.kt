package com.alphitardian.onboardingproject.data.auth.data_source.remote

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse

interface RemoteDataSource {
    suspend fun loginUser(requestBody: LoginRequest): TokenResponse
    suspend fun getToken(): TokenResponse
}