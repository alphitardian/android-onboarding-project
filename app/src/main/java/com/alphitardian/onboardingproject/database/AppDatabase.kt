package com.alphitardian.onboardingproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alphitardian.onboardingproject.data.user.data_source.local.database.NewsDao
import com.alphitardian.onboardingproject.data.user.data_source.local.database.UserDao
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

@Database(entities = [UserEntity::class, NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    abstract fun userDao(): UserDao
}