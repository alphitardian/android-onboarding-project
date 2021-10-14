package com.alphitardian.onboardingproject.data.auth.data_source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse

class RemoteDataSource(private val authApi: AuthApi) {
    suspend fun loginUser(requestBody: LoginRequest): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()
        val response = authApi.loginUser(requestBody)

        when {
            response.isSuccessful -> {
                result.postValue(response.body()
                    ?.let { Resource.Success<TokenResponse>(data = it) })
            }
            else -> {
                result.postValue(Resource.Error(state = ErrorState.fromRawValue(response.code())))
            }
        }

        return result
    }

    suspend fun getToken(userToken: String): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()
        val response = authApi.getUserToken(userToken)

        when {
            response.isSuccessful -> {
                result.postValue(response.body()
                    ?.let { Resource.Success<TokenResponse>(data = it) })
            }
            else -> {
                result.postValue(Resource.Error(state = ErrorState.fromRawValue(response.code())))
            }
        }

        return result
    }
}