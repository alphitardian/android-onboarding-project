package com.alphitardian.onboardingproject.data.auth.repository

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(authApi: AuthApi) : AuthRepository {

    private val remoteDataSource = RemoteDataSource(authApi)

    override suspend fun loginUser(requestBody: LoginRequest): LiveData<Resource<TokenResponse>> {
        return remoteDataSource.loginUser(requestBody)
    }

    override suspend fun getUserToken(userToken: String): LiveData<Resource<TokenResponse>> {
        return remoteDataSource.getToken(userToken)
    }
}