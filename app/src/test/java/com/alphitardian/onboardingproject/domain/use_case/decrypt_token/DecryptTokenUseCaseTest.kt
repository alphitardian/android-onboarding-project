package com.alphitardian.onboardingproject.domain.use_case.decrypt_token

import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.utils.DummyData
import com.alphitardian.onboardingproject.utils.FakeAndroidKeyStore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DecryptTokenUseCaseTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

    private lateinit var decryptUsecase : DecryptTokenUseCase
    private lateinit var encryptUsecase : EncryptTokenUseCase
    private lateinit var encryptionResult : HashMap<String, String>

    @Before
    fun setup() {
        encryptUsecase = EncryptTokenUseCase(KeystoreHelper)
        decryptUsecase = DecryptTokenUseCase(KeystoreHelper)
    }

    @Test
    fun testEncryptAndDecryptTokenSuccess() {
        runBlocking {
            encryptionResult = encryptUsecase(DummyData.userToken)

            val dummyData = encryptionResult
            val encryptedToken = dummyData["token"]
            val encryptedIV = dummyData["iv"]
            val actual = decryptUsecase(encryptedToken ?: "", encryptedIV ?: "")

            assertEquals(DummyData.userToken, actual)
        }
    }
}