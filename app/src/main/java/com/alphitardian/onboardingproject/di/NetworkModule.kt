package com.alphitardian.onboardingproject.di

import com.alphitardian.onboardingproject.data.auth.data_source.remote.network.AuthApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.network.UserApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSource as AuthRemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource as UserRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl("http://34.121.153.157/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authApi: AuthApi): AuthRemoteDataSource =
        AuthRemoteDataSource(authApi)

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userApi: UserApi): UserRemoteDataSource =
        UserRemoteDataSource(userApi)
}