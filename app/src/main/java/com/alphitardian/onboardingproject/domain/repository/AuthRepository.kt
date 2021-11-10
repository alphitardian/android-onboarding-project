package com.alphitardian.onboardingproject.domain.repository

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse

interface AuthRepository {
    suspend fun loginUser(requestBody: LoginRequest): TokenResponse
    suspend fun getUserToken(): TokenResponse
}