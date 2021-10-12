package com.alphitardian.onboardingproject.data.auth.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String,

    @SerialName("username")
    val username: String,
)