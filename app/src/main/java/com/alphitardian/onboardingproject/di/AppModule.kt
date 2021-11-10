package com.alphitardian.onboardingproject.di

import android.content.Context
import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.datastore.PrefStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideKeystore() : KeystoreHelper = KeystoreHelper

    @Provides
    fun provideDatastore(@ApplicationContext context: Context) : PrefStore = PrefStore(context)
}