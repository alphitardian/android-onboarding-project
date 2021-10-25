package com.alphitardian.onboardingproject.data.auth.data_source.remote

import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val authApi: AuthApi) : RemoteDataSource {
    override suspend fun loginUser(requestBody: LoginRequest): TokenResponse {
        return authApi.loginUser(requestBody)
    }

    override suspend fun getToken(userToken: String): TokenResponse {
        return authApi.getUserToken(userToken)
    }
}