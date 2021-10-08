package com.alphitardian.onboardingproject.data.repository

import com.alphitardian.onboardingproject.data.remote.api.AuthApi
import com.alphitardian.onboardingproject.data.remote.entity.auth.LoginRequest
import com.alphitardian.onboardingproject.data.remote.entity.auth.TokenResponse

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {
    override suspend fun loginUser(requestBody: LoginRequest): TokenResponse {
        return api.loginUser(requestBody)
    }

    override suspend fun getUserToken(userToken: String): TokenResponse {
        return api.getUserToken(userToken)
    }
}