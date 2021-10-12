package com.alphitardian.onboardingproject.domain.repository

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun loginUser(requestBody: LoginRequest): Response<TokenResponse>
    suspend fun getUserToken(userToken: String): Response<TokenResponse>
}