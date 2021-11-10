package com.alphitardian.onboardingproject.domain.use_case.get_news

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): List<NewsEntity>? {
        return repository.getNews()
    }
}