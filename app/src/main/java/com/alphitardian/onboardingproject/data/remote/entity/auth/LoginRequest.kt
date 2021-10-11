package com.alphitardian.onboardingproject.data.remote.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String,

    @SerialName("username")
    val username: String,
)