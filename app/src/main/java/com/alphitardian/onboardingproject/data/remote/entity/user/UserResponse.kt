package com.alphitardian.onboardingproject.data.remote.entity.user

data class UserResponse(
    val username: String,
    val name: String,
    val bio: String,
    val web: String,
    val picture: String,
)