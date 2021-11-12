package com.alphitardian.onboardingproject.data.datastore

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DatastoreTest : FakeDatastore() {

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var prefStore: PrefStore

    @Before
    fun setup() {
        prefStore = PrefStore(context, datastore)
    }

    @Test
    fun testInsertAndGetToken() {
        runBlocking {
            val dummyToken = DummyData.userToken
            prefStore.saveToken(dummyToken)

            val getToken = prefStore.userToken.firstOrNull()
            Assert.assertEquals(getToken, dummyToken)
        }
    }

    @Test
    fun testInsertAndGetTokenIV() {
        runBlocking {
            val dummyIV = "InitializationVector"
            prefStore.saveTokenInitializationVector(dummyIV)

            val getIV = prefStore.tokenInitializationVector.firstOrNull()
            Assert.assertEquals(getIV, dummyIV)
        }
    }

    @Test
    fun testInsertAndGetExpiredTime() {
        runBlocking {
            val dummyExpiredTime = 3600L
            prefStore.saveExpiredTime(dummyExpiredTime)

            val getExpiredTime = prefStore.userExpired.firstOrNull()
            Assert.assertEquals(getExpiredTime, dummyExpiredTime)
        }
    }
}