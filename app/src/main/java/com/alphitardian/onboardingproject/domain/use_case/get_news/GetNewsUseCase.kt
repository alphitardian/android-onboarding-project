package com.alphitardian.onboardingproject.domain.use_case.get_news

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): NewsResponse {
        return repository.getNews()
    }
}