package com.alphitardian.onboardingproject.data.remote.entity.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("username")
    val username: String,

    @SerialName("name")
    val name: String,

    @SerialName("bio")
    val bio: String,

    @SerialName("web")
    val web: String,

    @SerialName("picture")
    val picture: String,
)