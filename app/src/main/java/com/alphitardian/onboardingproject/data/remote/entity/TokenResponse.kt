package com.alphitardian.onboardingproject.data.remote.entity

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    val token: String,
    val scheme: String,
    @SerializedName("expires_at")
    val expiresTime: String,
)
