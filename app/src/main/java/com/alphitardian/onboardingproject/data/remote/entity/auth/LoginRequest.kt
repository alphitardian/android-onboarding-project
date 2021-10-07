package com.alphitardian.onboardingproject.data.remote.entity.auth

data class LoginRequest(
    val password: String,
    val username: String,
)