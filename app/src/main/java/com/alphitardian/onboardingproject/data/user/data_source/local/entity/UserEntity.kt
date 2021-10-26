package com.alphitardian.onboardingproject.data.user.data_source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "bio")
    val bio: String,

    @ColumnInfo(name = "web")
    val web: String,

    @ColumnInfo(name = "picture")
    val picture: String,
)