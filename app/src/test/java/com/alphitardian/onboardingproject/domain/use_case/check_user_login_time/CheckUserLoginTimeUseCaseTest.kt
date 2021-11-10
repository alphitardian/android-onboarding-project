package com.alphitardian.onboardingproject.domain.use_case.check_user_login_time

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.datastore.PrefStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CheckUserLoginTimeUseCaseTest {

    private var context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var datastore : PrefStore

    @Mock
    private lateinit var checkUserLoginTimeUseCase : CheckUserLoginTimeUseCase

    @Before
    fun setup() {
        datastore = PrefStore(context)
        checkUserLoginTimeUseCase = CheckUserLoginTimeUseCase(datastore)
    }

    @After
    fun teardown() {
        File(ApplicationProvider.getApplicationContext<Context>().filesDir, "datastore").deleteRecursively()
    }

    private fun emptyFunction() = Unit

    @Test
    fun testUserLoggedinPassed() {
        runBlocking {
            val dummyExpiredTime = Date().toInstant().epochSecond + 3600
            datastore.saveExpiredTime(dummyExpiredTime)

            delay(1000)

            val actual = checkUserLoginTimeUseCase { emptyFunction() }
            val expected = true

            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun testUserLoggedinFailed() {
        runBlocking {
            val dummyExpiredTime = Date().toInstant().epochSecond
            datastore.saveExpiredTime(dummyExpiredTime)

            delay(1000)

            val actual = checkUserLoginTimeUseCase { emptyFunction() }
            val expected = false

            Assert.assertEquals(expected, actual)
        }
    }

}