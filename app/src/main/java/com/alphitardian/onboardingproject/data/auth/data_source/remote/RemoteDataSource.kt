package com.alphitardian.onboardingproject.data.auth.data_source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val authApi: AuthApi) {
    suspend fun loginUser(requestBody: LoginRequest): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()

        try {
            val response = authApi.loginUser(requestBody)
            result.postValue(Resource.Success<TokenResponse>(data = response))
        } catch (error: Throwable) {
            result.postValue(Resource.Error<TokenResponse>(error = error))
        }

        return result
    }

    suspend fun getToken(userToken: String): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()

        try {
            val response = authApi.getUserToken(userToken)
            result.postValue(Resource.Success<TokenResponse>(data = response))
        } catch (error: Throwable) {
            result.postValue(Resource.Error<TokenResponse>(error = error))
        }

        return result
    }
}