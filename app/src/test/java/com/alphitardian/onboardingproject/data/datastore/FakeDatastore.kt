package com.alphitardian.onboardingproject.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import java.io.File

abstract class FakeDatastore {
    private lateinit var scope: CoroutineScope
    protected lateinit var datastore: DataStore<Preferences>

    @Before
    fun createDatastore() {
        scope = CoroutineScope(Dispatchers.IO + Job())
        datastore = PreferenceDataStoreFactory.create(scope = scope) {
            InstrumentationRegistry.getInstrumentation().targetContext.preferencesDataStoreFile(
                "test-datastore"
            )
        }
    }

    @After
    fun removeDatastore() {
        File(ApplicationProvider.getApplicationContext<Context>().filesDir,
            "datastore").deleteRecursively()
        scope.cancel()
    }
}