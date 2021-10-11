package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelResponse(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,
)
