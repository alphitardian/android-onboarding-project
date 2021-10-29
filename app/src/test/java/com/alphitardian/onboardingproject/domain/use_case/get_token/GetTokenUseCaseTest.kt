package com.alphitardian.onboardingproject.domain.use_case.get_token

import com.alphitardian.onboardingproject.domain.repository.auth.FakeAuthRepository
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTokenUseCaseTest {
    private val repository = FakeAuthRepository()
    private val usecase = GetTokenUseCase(repository)

    @After
    fun teardown() {
        repository.teardown()
    }

    @Test
    fun getToken() {
        runBlocking {
            val actual = usecase(DummyData.userToken)
            val expected = DummyData.expectedTokenResponse

            assertEquals(expected, actual)
        }
    }
}