package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemResponse(
    val id: Int,
    val title: String,
    val url: String,
    @SerialName("cover_image")
    val coverImage: String,
    val nsfw: Boolean,
    val channel: ChannelResponse,
    val counter: CounterResponse,
    @SerialName("created_at")
    val createdTime: String,
)
