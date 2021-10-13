package com.alphitardian.onboardingproject.domain.use_case.get_profile

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(userToken: String): LiveData<Resource<UserResponse>> {
        return repository.getUserProfile(userToken)
    }
}