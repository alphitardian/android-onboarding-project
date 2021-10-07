package com.alphitardian.onboardingproject.data.remote.entity.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val username: String,
)