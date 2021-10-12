package com.alphitardian.onboardingproject.data.auth.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("token")
    val token: String,

    @SerialName("scheme")
    val scheme: String,

    @SerialName("expires_at")
    val expiresTime: String,
)
