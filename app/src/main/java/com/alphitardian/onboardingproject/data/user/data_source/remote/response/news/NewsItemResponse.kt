package com.alphitardian.onboardingproject.data.user.data_source.remote.response.news

import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("url")
    val url: String,

    @SerialName("cover_image")
    val coverImage: String,

    @SerialName("nsfw")
    val nsfw: Boolean,

    @SerialName("channel")
    val channel: ChannelResponse,

    @SerialName("counter")
    val counter: CounterResponse,

    @SerialName("created_at")
    val createdTime: String,
)

fun NewsItemResponse.toNewsEntity() : NewsEntity {
    return NewsEntity(
        id = id,
        title = title,
        url = url,
        coverImage = coverImage,
        nsfw = nsfw,
        channel = channel.name,
        view = counter.view,
        comment = counter.comment,
        upVote = counter.upVote,
        downVote = counter.downVote,
        createdTime = createdTime
    )
}
