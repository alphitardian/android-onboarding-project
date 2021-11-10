package com.alphitardian.onboardingproject.data.user.data_source.remote.response.user

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
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

fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        username = username,
        name = name,
        bio = bio,
        web = web,
        picture = picture
    )
}