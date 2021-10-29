package com.alphitardian.onboardingproject.data.user.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.common.NetworkHelper
import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSource
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.M)
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext private val context: Context
) : UserRepository {
    override suspend fun getUserProfile(userToken: String): UserEntity? {
        var user: UserEntity?
        var response: UserResponse?
        withContext(Dispatchers.IO) {
            user = localDataSource.getUserProfile()

            if (user == null) {
                if (NetworkHelper.isNetworkAvailable(context)) {
                    response = remoteDataSource.getProfile(userToken)
                    response?.let { user = it.toUserEntity() }
                }

                user?.let { localDataSource.insertProfile(it) }
            }
        }
        return user
    }

    override suspend fun getNews(userToken: String): List<NewsEntity>? {
        var news: List<NewsEntity>?
        var response: NewsResponse?
        withContext(Dispatchers.IO) {
            news = localDataSource.getNews()

            if (news?.isEmpty() == true || news?.isNotEmpty() == true) {
                if (NetworkHelper.isNetworkAvailable(context)) {
                    response = remoteDataSource.getNews(userToken)
                    news?.forEachIndexed { index, newsEntity ->
                        if (newsEntity == response?.data?.get(index)?.toNewsEntity()) {
                            return@withContext
                        }
                    }
                    news = response?.data?.map {
                        it.toNewsEntity()
                    }
                }

                news?.map {
                    localDataSource.insertNews(it)
                }
            }
        }
        return news
    }
}