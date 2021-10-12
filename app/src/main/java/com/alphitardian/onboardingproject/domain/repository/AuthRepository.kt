package com.alphitardian.onboardingproject.domain.repository

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun loginUser(requestBody: LoginRequest): LiveData<Resource<TokenResponse>>
    suspend fun getUserToken(userToken: String): LiveData<Resource<TokenResponse>>
}