package com.alphitardian.onboardingproject.data.auth.repository

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    AuthRepository {

    override suspend fun loginUser(requestBody: LoginRequest): Resource<TokenResponse> {
        return remoteDataSource.loginUser(requestBody)
    }

    override suspend fun getUserToken(userToken: String): Resource<TokenResponse> {
        return remoteDataSource.getToken(userToken)
    }
}