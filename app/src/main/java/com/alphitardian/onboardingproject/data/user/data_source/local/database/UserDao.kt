package com.alphitardian.onboardingproject.data.user.data_source.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUserProfile() : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(user: UserEntity)
}