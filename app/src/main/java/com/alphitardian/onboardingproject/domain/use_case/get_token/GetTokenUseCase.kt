package com.alphitardian.onboardingproject.domain.use_case.get_token

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(userToken: String): LiveData<Resource<TokenResponse>> {
        return repository.getUserToken(userToken)
    }
}