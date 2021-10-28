package com.alphitardian.onboardingproject.di

import com.alphitardian.onboardingproject.common.KeystoreHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object KeystoreModule {
    @Provides
    fun provideKeystore() : KeystoreHelper = KeystoreHelper
}