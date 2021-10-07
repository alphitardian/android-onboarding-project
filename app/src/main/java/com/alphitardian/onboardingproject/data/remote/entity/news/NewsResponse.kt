package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val data: List<NewsItemResponse>,
)
