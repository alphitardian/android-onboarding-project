package com.alphitardian.onboardingproject.domain.repository.auth

import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSourceImpl
import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import com.alphitardian.onboardingproject.utils.DummyData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import retrofit2.Retrofit
import java.io.File

class FakeAuthRepository : AuthRepository {
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

    override suspend fun loginUser(requestBody: LoginRequest): TokenResponse {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}token-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        return datasource.loginUser(requestBody)
    }

    override suspend fun getUserToken(): TokenResponse {
        mockWebServer.enqueue(MockResponse().setResponseCode(200)
            .setBody(File("${DummyData.BASE_PATH}token-response.json").inputStream().readBytes()
                .toString(Charsets.UTF_8)))

        return datasource.getToken()
    }
}