package com.alphitardian.onboardingproject.data.user

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.IndexOutOfBoundsException

class UserLocalDataSourceTest {
    private val dataSource = FakeLocalDataSource()

    @Test
    fun testAddAndGetUserProfile() {
        runBlocking {
            val user = DummyData.expectedProfileResponse.toUserEntity()
            dataSource.insertProfile(user)
            val expected = dataSource.getUserProfile()
            assertEquals(expected, user)
        }
    }

    @Test
    fun testGetEmptyUserProfile() {
        runBlocking {
            try {
                dataSource.getUserProfile()
                assert(false)
            } catch (error: IndexOutOfBoundsException) {
                assert(true)
            }
        }
    }

    @Test
    fun testAddAndGetNews() {
        runBlocking {
            val news = DummyData.expectedSingleNewsResponse.toNewsEntity()
            dataSource.insertNews(news)
            val expected = dataSource.getNews()[0]
            assertEquals(expected, news)
        }
    }

    @Test
    fun testAddAndGetAllNews() {
        runBlocking {
            val news = DummyData.expectedSingleNewsResponse.toNewsEntity()
            for (i in 1..5) {
                dataSource.insertNews(news)
            }
            val newsList = dataSource.getNews()
            assertEquals(newsList.size, 5)
        }
    }

    @Test
    fun testGetEmptyNews() {
        runBlocking {
            val news = dataSource.getNews()
            assertEquals(0, news.size)
        }
    }
}