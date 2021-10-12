package com.alphitardian.onboardingproject.data.user.data_source.remote.response.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("data")
    val data: List<NewsItemResponse>,
)
