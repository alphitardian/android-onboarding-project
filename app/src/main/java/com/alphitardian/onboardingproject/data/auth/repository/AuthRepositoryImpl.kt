package com.alphitardian.onboardingproject.data.auth.repository

import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {
    override suspend fun loginUser(requestBody: LoginRequest): Response<TokenResponse> {
        return api.loginUser(requestBody)
    }

    override suspend fun getUserToken(userToken: String): Response<TokenResponse> {
        return api.getUserToken(userToken)
    }
}