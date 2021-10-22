package com.alphitardian.onboardingproject.data.user.repository

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : UserRepository {
    override suspend fun getUserProfile(userToken: String): Resource<UserResponse> {
        return remoteDataSource.getProfile(userToken)
    }

    override suspend fun getNews(userToken: String): Resource<NewsResponse> {
        return remoteDataSource.getNews(userToken)
    }
}