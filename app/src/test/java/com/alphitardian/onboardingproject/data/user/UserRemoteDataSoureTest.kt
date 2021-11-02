package com.alphitardian.onboardingproject.data.user

import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSourceImpl
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.alphitardian.onboardingproject.utils.DummyData
import com.alphitardian.onboardingproject.utils.DummyData.BASE_PATH
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.File

class UserRemoteDataSoureTest {
    private val mockWebServer = MockWebServer()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UserApi::class.java)
    private val datasource = RemoteDataSourceImpl(api)

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUserProfileSuccess() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${BASE_PATH}profile-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            val actual = datasource.getProfile(DummyData.userToken)
            val expected = DummyData.expectedProfileResponse

            assertEquals(expected, actual)
        }
    }

    @Test
    fun testGetUserProfileFailed_error401() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401)
            .setBody(File("${BASE_PATH}user-response-401.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            try {
                datasource.getProfile(DummyData.userToken)
                assert(false)
            } catch (error: Exception) {
                if (error is HttpException) {
                    assertEquals(401, error.code())
                    assert(true)
                }
            }
        }
    }

    @Test
    fun testGetUserNewsSuccess() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${BASE_PATH}news-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            val actual = datasource.getNews(DummyData.userToken)
            val expected = DummyData.expectedNewsResponse

            assertEquals(expected, actual)
        }
    }

    @Test
    fun testGetUserNewsFailed_error401() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401)
            .setBody(File("${BASE_PATH}user-response-401.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            try {
                datasource.getNews(DummyData.userToken)
                assert(false)
            } catch (error: Exception) {
                if (error is HttpException) {
                    assertEquals(401, error.code())
                    assert(true)
                }
            }
        }
    }
}