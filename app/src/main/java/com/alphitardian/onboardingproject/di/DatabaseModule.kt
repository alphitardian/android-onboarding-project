package com.alphitardian.onboardingproject.di

import android.content.Context
import androidx.room.Room
import com.alphitardian.onboardingproject.data.user.data_source.local.LocalDataSourceImpl
import com.alphitardian.onboardingproject.data.user.data_source.local.database.NewsDao
import com.alphitardian.onboardingproject.data.user.data_source.local.database.UserDao
import com.alphitardian.onboardingproject.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "NewsAppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
}