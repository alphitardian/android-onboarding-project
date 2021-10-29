package com.alphitardian.onboardingproject.domain.use_case.get_profile

import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.domain.repository.user.FakeUserRepository
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProfileUseCaseTest {
    private val repository = FakeUserRepository()
    private val usecase = GetProfileUseCase(repository)

    @After
    fun teardown() {
        repository.teardown()
    }

    @Test
    fun getProfile() {
        runBlocking {
            val actual = usecase(DummyData.userToken)
            val expected = DummyData.expectedProfileResponse.toUserEntity()

            assertEquals(expected, actual)
        }
    }
}