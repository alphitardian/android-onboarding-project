package com.alphitardian.onboardingproject.domain.use_case.check_user_login_time

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.data.datastore.FakeDatastore
import com.alphitardian.onboardingproject.datastore.PrefStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CheckUserLoginTimeUseCaseTest : FakeDatastore() {

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var prefStore: PrefStore
    private lateinit var checkUserLoginTimeUseCase: CheckUserLoginTimeUseCase

    @Before
    fun setup() {
        prefStore = PrefStore(context, datastore)
        checkUserLoginTimeUseCase = CheckUserLoginTimeUseCase(prefStore)
    }

    @Test
    fun testUserLoggedinPassed() {
        runBlocking {
            val dummyExpiredTime = Date().toInstant().epochSecond + 3600
            prefStore.saveExpiredTime(dummyExpiredTime)

            delay(1000)

            val actual = checkUserLoginTimeUseCase { }
            val expected = true

            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun testUserLoggedinFailed() {
        runBlocking {
            val dummyExpiredTime = Date().toInstant().epochSecond
            prefStore.saveExpiredTime(dummyExpiredTime)

            delay(1000)

            val actual = checkUserLoginTimeUseCase { }
            val expected = false

            Assert.assertEquals(expected, actual)
        }
    }

}