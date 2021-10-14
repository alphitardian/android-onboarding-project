package com.alphitardian.onboardingproject.data.auth.data_source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val authApi: AuthApi) {
    suspend fun loginUser(requestBody: LoginRequest): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()
        val response = authApi.loginUser(requestBody)

        when {
            response.isSuccessful -> {
                result.postValue(Resource.Success<TokenResponse>(data = response.body()!!))
            }
            response.code() == 400 -> {
                result.postValue(Resource.Error<TokenResponse>(state = ErrorState.ERROR_400))
            }
            response.code() == 401 -> {
                result.postValue(Resource.Error<TokenResponse>(state = ErrorState.ERROR_401))
            }
            response.code() == 422 -> {
                result.postValue(Resource.Error<TokenResponse>(state = ErrorState.ERROR_422))
            }
        }

        return result
    }

    suspend fun getToken(userToken: String): LiveData<Resource<TokenResponse>> {
        val result = MutableLiveData<Resource<TokenResponse>>()
        val response = authApi.getUserToken(userToken)

        when {
            response.isSuccessful -> {
                result.postValue(Resource.Success<TokenResponse>(data = response.body()!!))
            }
            response.code() == 401 -> {
                result.postValue(Resource.Error<TokenResponse>(state = ErrorState.ERROR_401))
            }
        }

        return result
    }
}