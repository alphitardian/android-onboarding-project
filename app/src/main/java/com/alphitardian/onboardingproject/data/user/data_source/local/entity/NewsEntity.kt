package com.alphitardian.onboardingproject.data.user.data_source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "cover_image")
    val coverImage: String,

    @ColumnInfo(name = "nsfw")
    val nsfw: Boolean,

    @ColumnInfo(name = "channel")
    val channel: String,

    @ColumnInfo(name = "upvote")
    val upVote: Int,

    @ColumnInfo(name = "downvote")
    val downVote: Int,

    @ColumnInfo(name = "comment")
    val comment: Int,

    @ColumnInfo(name = "view")
    val view: Int,

    @ColumnInfo(name = "created_time")
    val createdTime: String,
)