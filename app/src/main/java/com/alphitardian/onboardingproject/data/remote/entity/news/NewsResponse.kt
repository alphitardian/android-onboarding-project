package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("data")
    val data: List<NewsItemResponse>,
)
