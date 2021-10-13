package com.alphitardian.onboardingproject.domain.use_case.get_news

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(userToken: String): LiveData<Resource<NewsResponse>> {
        return repository.getNews(userToken)
    }
}