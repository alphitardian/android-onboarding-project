package com.alphitardian.onboardingproject.domain.repository.user

import com.alphitardian.onboardingproject.data.user.FakeLocalDataSource
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSourceImpl
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import com.alphitardian.onboardingproject.utils.DummyData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import retrofit2.Retrofit
import java.io.File

class FakeUserRepository : UserRepository {
    private val mockWebServer = MockWebServer()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UserApi::class.java)
    private val remoteDataSource = RemoteDataSourceImpl(api)
    private val localDataSource = FakeLocalDataSource()

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    override suspend fun getUserProfile(): UserEntity? {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}profile-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        var userEntity: UserEntity? = null
        runBlocking {
            if (userEntity == null) {
                val response = remoteDataSource.getProfile()
                if (userEntity == response.toUserEntity()) return@runBlocking
                userEntity = response.toUserEntity()
                localDataSource.insertProfile(userEntity!!)
            } else {
                userEntity = localDataSource.getUserProfile()
            }
        }
        return userEntity
    }

    override suspend fun getNews(): List<NewsEntity>? {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}news-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        var newsList: List<NewsEntity>? = null
        runBlocking {
            newsList = localDataSource.getNews()

            val response = remoteDataSource.getNews().data

            newsList?.forEachIndexed { index, entity ->
                if (entity == response[index].toNewsEntity()) return@runBlocking
            }
            newsList = response.map {
                it.toNewsEntity()
            }
            newsList?.map {
                localDataSource.insertNews(it)
            }
        }
        return newsList
    }
}