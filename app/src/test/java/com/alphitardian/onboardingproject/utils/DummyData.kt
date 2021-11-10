package com.alphitardian.onboardingproject.utils

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.ChannelResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.CounterResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsItemResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse

object DummyData {
    const val BASE_PATH = "../app/src/test/assets/"
    const val userToken =
        "dGVzdGVyfDIwMjEtMTAtMjZUMDQ6MTQ6MTdafGNkOWIzZGU5YzMwZTc5ZjdjOTc4MTY4ZGJiNWMwNGU5ZmY0MDExMDc3M2U1MzE3ZmU5OGU1ZjgyN2I4MzRkM2M="

    val expectedTokenResponse = TokenResponse(
        token = "dGVzdGVyfDIwMjEtMTAtMjZUMDQ6MTQ6MTdafGNkOWIzZGU5YzMwZTc5ZjdjOTc4MTY4ZGJiNWMwNGU5ZmY0MDExMDc3M2U1MzE3ZmU5OGU1ZjgyN2I4MzRkM2M=",
        scheme = "Bearer",
        expiresTime = "2021-10-26T04:14:17Z"
    )

    val expectedProfileResponse = UserResponse(
        username = "tester",
        name = "Prapto Prawirodirjo",
        bio = "5 terlalu banyak, 10 kurang, lorem ipsum dolor si jamet \uD83C\uDFB8",
        web = "https://icehousecorp.com",
        picture = "https://pbs.twimg.com/profile_images/554798224154701824/mWd3laxO_400x400.png"
    )

    val expectedSingleNewsResponse = NewsItemResponse(
        id = 1,
        title = "Garlic ice-cream was her favorite #9801",
        url = "https://www.google.fi/search?q=Garlic+ice-cream+was+her+favorite",
        coverImage = "https://place-hold.it/600x400/ddd/000000.jpg&text=Image-0&bold&fontsize=20",
        nsfw = false,
        createdTime = "2021-10-26T02:17:31Z",
        channel = ChannelResponse(
            id = 1,
            name = "r/tycoon"
        ),
        counter = CounterResponse(
            view = 9119,
            upVote = 383,
            downVote = 420,
            comment = 64
        )
    )

    val expectedNewsResponse = NewsResponse(
        data = listOf(
            NewsItemResponse(
                id = 1,
                title = "Garlic ice-cream was her favorite #9801",
                url = "https://www.google.fi/search?q=Garlic+ice-cream+was+her+favorite",
                coverImage = "https://place-hold.it/600x400/ddd/000000.jpg&text=Image-0&bold&fontsize=20",
                nsfw = false,
                createdTime = "2021-10-26T02:17:31Z",
                channel = ChannelResponse(
                    id = 1,
                    name = "r/tycoon"
                ),
                counter = CounterResponse(
                    view = 9119,
                    upVote = 383,
                    downVote = 420,
                    comment = 64
                )
            ),
            NewsItemResponse(
                id = 2,
                title = "I think I will buy the red car, or I will lease the blue one #7725",
                url = "https://www.google.fi/search?q=I+think+I+will+buy+the+red+car%2C+or+I+will+lease+the+blue+one",
                coverImage = "https://place-hold.it/600x400/ddd/000000.jpg&text=Image-1&bold&fontsize=20",
                nsfw = true,
                createdTime = "2021-10-26T02:17:31Z",
                channel = ChannelResponse(
                    id = 2,
                    name = "r/expect"
                ),
                counter = CounterResponse(
                    view = 7249,
                    upVote = 76,
                    downVote = 55,
                    comment = 63
                )
            )
        )
    )
}