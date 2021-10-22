package com.alphitardian.onboardingproject.domain.use_case.user_login

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(requestBody: LoginRequest): Resource<TokenResponse> {
        return authRepository.loginUser(requestBody)
    }
}