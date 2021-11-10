package com.alphitardian.onboardingproject.domain.use_case.get_profile

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): UserEntity? {
        return repository.getUserProfile()
    }
}