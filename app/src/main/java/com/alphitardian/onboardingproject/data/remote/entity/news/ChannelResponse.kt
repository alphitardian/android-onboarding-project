package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.Serializable

@Serializable
data class ChannelResponse(
    val id: String,
    val name: String,
)
