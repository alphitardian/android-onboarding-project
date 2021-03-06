package com.alphitardian.onboardingproject.domain.use_case.get_news

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.domain.repository.user.FakeUserRepository
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNewsUseCaseTest {
    private val repository = FakeUserRepository()
    private val getNewsUseCase = GetNewsUseCase(repository)

    @After
    fun teardown() {
        repository.teardown()
    }

    @Test
    fun testGetNewsSuccess() {
        runBlocking {
            val actual = getNewsUseCase()
            val expected = DummyData.expectedNewsResponse.data.map { it.toNewsEntity() }

            assertEquals(expected, actual)
        }
    }
}