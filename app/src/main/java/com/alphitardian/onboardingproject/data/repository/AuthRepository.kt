package com.alphitardian.onboardingproject.data.repository

import com.alphitardian.onboardingproject.data.remote.entity.auth.LoginRequest
import com.alphitardian.onboardingproject.data.remote.entity.auth.TokenResponse

interface AuthRepository {
    suspend fun loginUser(requestBody: LoginRequest): TokenResponse
    suspend fun getUserToken(userToken: String): TokenResponse
}