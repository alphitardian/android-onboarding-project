package com.alphitardian.onboardingproject.data.user.repository

import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSource
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : UserRepository {
    override suspend fun getUserProfile(userToken: String): UserEntity? {
        var user : UserEntity?
        var response: UserResponse?
        withContext(Dispatchers.IO) {
            user = localDataSource.getUserProfile()

            if (user == null) {
                response = remoteDataSource.getProfile(userToken)
                response?.let { user = it.toUserEntity() }
                user?.let { localDataSource.insertProfile(it) }
            }
        }
        return user
    }

    override suspend fun getNews(userToken: String): NewsResponse {
        return remoteDataSource.getNews(userToken)
    }
}