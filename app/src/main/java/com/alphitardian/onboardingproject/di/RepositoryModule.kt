package com.alphitardian.onboardingproject.di

import com.alphitardian.onboardingproject.data.auth.repository.AuthRepositoryImpl
import com.alphitardian.onboardingproject.data.user.repository.UserRepositoryImpl
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}