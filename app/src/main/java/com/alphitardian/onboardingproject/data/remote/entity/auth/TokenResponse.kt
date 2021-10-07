package com.alphitardian.onboardingproject.data.remote.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String,
    val scheme: String,
    @SerialName("expires_at")
    val expiresTime: String,
)
