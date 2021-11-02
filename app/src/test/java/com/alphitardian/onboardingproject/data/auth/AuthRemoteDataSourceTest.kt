package com.alphitardian.onboardingproject.data.auth

import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSourceImpl
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.utils.DummyData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import java.io.File

class AuthRemoteDataSourceTest {
    private val mockWebServer = MockWebServer()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AuthApi::class.java)
    private val datasource = RemoteDataSourceImpl(api)

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testLoginSuccess() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}token-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            val requestBody = LoginRequest("tester123", "tester")
            val actual = datasource.loginUser(requestBody)
            val expected = DummyData.expectedTokenResponse

            assertEquals(expected, actual)
        }
    }

    @Test
    fun testGetNewTokenSuccess() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}token-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        runBlocking {
            val actual = datasource.getToken(DummyData.userToken)
            val expected = DummyData.expectedTokenResponse

            assertEquals(expected, actual)
        }
    }
}