package com.alphitardian.onboardingproject.data.auth.data_source.remote

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val authApi: AuthApi) : RemoteDataSource {
    override suspend fun loginUser(requestBody: LoginRequest): Resource<TokenResponse> {
        return try {
            val response = authApi.loginUser(requestBody)
            Resource.Success<TokenResponse>(data = response)
        } catch (error: Throwable) {
            Resource.Error<TokenResponse>(error = error)
        }
    }

    override suspend fun getToken(userToken: String): Resource<TokenResponse> {
        return try {
            val response = authApi.getUserToken(userToken)
            Resource.Success<TokenResponse>(data = response)
        } catch (error: Throwable) {
            Resource.Error<TokenResponse>(error = error)
        }
    }
}