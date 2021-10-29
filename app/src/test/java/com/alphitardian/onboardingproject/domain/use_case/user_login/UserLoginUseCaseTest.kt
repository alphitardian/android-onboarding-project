package com.alphitardian.onboardingproject.domain.use_case.user_login

import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.domain.repository.auth.FakeAuthRepository
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test

class UserLoginUseCaseTest {
    private val repository = FakeAuthRepository()
    private val usecase = UserLoginUseCase(repository)

    @After
    fun teardown() {
        repository.teardown()
    }

    @Test
    fun userLogin() {
        runBlocking {
            val requestBody = LoginRequest(password = "tester123", username = "tester")
            val actual = usecase(requestBody)
            val expected = DummyData.expectedTokenResponse

            Assert.assertEquals(expected, actual)
        }
    }
}