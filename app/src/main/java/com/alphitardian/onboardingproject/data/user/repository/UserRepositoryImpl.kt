package com.alphitardian.onboardingproject.data.user.repository

import androidx.lifecycle.LiveData
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import retrofit2.Response

class UserRepositoryImpl(private val remoteDataSource: RemoteDataSource) : UserRepository {
    override suspend fun getUserProfile(userToken: String): LiveData<Resource<UserResponse>> {
        return remoteDataSource.getProfile(userToken)
    }

    override suspend fun getNews(userToken: String): LiveData<Resource<NewsResponse>> {
        return remoteDataSource.getNews(userToken)
    }
}