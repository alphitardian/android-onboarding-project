package com.alphitardian.onboardingproject.data.user.data_source.remote.response.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelResponse(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,
)
