package com.alphitardian.onboardingproject.data.user

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserLocalDataSourceTest {
    private val dataSource = FakeLocalDataSource()

    @Test
    fun addAndGetUser() {
        runBlocking {
            val user = DummyData.expectedProfileResponse.toUserEntity()
            dataSource.insertProfile(user)
            val expected = dataSource.getUserProfile()
            assertEquals(expected, user)
        }
    }

    @Test
    fun addAndGetNews() {
        runBlocking {
            val news = DummyData.expectedSingleNewsResponse.toNewsEntity()
            dataSource.insertNews(news)
            val expected = dataSource.getNews()[0]
            assertEquals(expected, news)
        }
    }

    @Test
    fun addAndGetAllNews() {
        runBlocking {
            val news = DummyData.expectedSingleNewsResponse.toNewsEntity()
            for (i in 1..5) {
                dataSource.insertNews(news)
            }
            val newsList = dataSource.getNews()
            assertEquals(newsList.size, 5)
        }
    }
}