package com.alphitardian.onboardingproject.data.remote.entity

import com.google.gson.annotations.SerializedName

data class NewsItemResponse(
    val id: Int,
    val title: String,
    val url: String,
    @SerializedName("cover_image")
    val coverImage: String,
    val nsfw: Boolean,
    val channel: ChannelResponse,
    val counter: CounterResponse,
    @SerializedName("created_at")
    val createdTime: String,
)
