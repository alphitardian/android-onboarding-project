package com.alphitardian.onboardingproject.data.user.data_source.local.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserProfile(username: String) : LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(user: UserEntity)
}