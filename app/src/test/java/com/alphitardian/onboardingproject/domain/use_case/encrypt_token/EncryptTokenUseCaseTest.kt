package com.alphitardian.onboardingproject.domain.use_case.encrypt_token

import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.utils.DummyData
import com.alphitardian.onboardingproject.utils.FakeAndroidKeyStore
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class EncryptTokenUseCaseTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

    private lateinit var usecase : EncryptTokenUseCase

    @Before
    fun setup() {
        usecase = EncryptTokenUseCase(KeystoreHelper)
    }

    @Test
    fun encryptToken() {
        val actual = usecase(DummyData.userToken)

        assertNotNull(actual)
    }
}