package com.alphitardian.onboardingproject.domain.use_case.get_token

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): TokenResponse {
        return repository.getUserToken()
    }
}