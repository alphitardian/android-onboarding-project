package com.alphitardian.onboardingproject.data.auth.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("message")
    val message: String,

    @SerialName("fields")
    val fields: List<Field>,
)

@Serializable
data class Field(
    @SerialName("name")
    val name: String,

    @SerialName("error")
    val error: String,
)
