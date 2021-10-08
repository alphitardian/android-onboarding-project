package com.alphitardian.onboardingproject.domain.use_case.get_news

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.remote.entity.news.NewsResponse
import com.alphitardian.onboardingproject.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userToken: String): Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getNews(userToken)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}