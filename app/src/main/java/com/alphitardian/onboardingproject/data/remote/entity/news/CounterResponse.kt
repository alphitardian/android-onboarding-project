package com.alphitardian.onboardingproject.data.remote.entity.news

import com.google.gson.annotations.SerializedName

data class CounterResponse(
    @SerializedName("upvote")
    val upVote: Int,
    @SerializedName("downvote")
    val downVote: Int,
    val comment: Int,
    val view: Int,
)
