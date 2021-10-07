package com.alphitardian.onboardingproject.data.remote.entity.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CounterResponse(
    @SerialName("upvote")
    val upVote: Int,
    @SerialName("downvote")
    val downVote: Int,
    val comment: Int,
    val view: Int,
)
