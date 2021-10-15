package com.alphitardian.onboardingproject.di

import com.alphitardian.onboardingproject.data.auth.repository.AuthRepositoryImpl
import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSource
import com.alphitardian.onboardingproject.data.user.repository.UserRepositoryImpl
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSource as AuthRemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource as UserRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(remoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: LocalDataSource,
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource)
    }
}