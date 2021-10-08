package com.alphitardian.onboardingproject.domain.use_case.user_login

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.remote.entity.auth.LoginRequest
import com.alphitardian.onboardingproject.data.remote.entity.auth.TokenResponse
import com.alphitardian.onboardingproject.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserLoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(requestBody : LoginRequest): Flow<Resource<TokenResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.loginUser(requestBody)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error<TokenResponse>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<TokenResponse>("Couldn't reach server. Check your internet connection."))
        }
    }
}