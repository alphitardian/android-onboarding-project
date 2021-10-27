package com.alphitardian.onboardingproject.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val STORE_NAME = "token_datastore"
private val USER_TOKEN = stringPreferencesKey(name = "user_token")
private val USER_TOKEN_IV = stringPreferencesKey(name = "user_token_iv")
private val USER_EXPIRED = longPreferencesKey(name = "user_expired")

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)

@Singleton
class PrefStore @Inject constructor(@ApplicationContext context: Context) {
    private val settingDataStore = context.datastore

    val userToken = settingDataStore.data.map { preference ->
        val token = preference[USER_TOKEN] ?: ""
        token
    }

    val tokenInitializationVector = settingDataStore.data.map { preference ->
        val iv = preference[USER_TOKEN_IV] ?: ""
        iv
    }

    val userExpired = settingDataStore.data.map { preference ->
        val time = preference[USER_EXPIRED] ?: 0
        time
    }

    suspend fun saveToken(token: String) {
        settingDataStore.edit { preference ->
            preference[USER_TOKEN] = token
        }
    }

    suspend fun saveTokenInitializationVector(iv: String) {
        settingDataStore.edit { preference ->
            preference[USER_TOKEN_IV] = iv
        }
    }

    suspend fun saveExpiredTime(time: Long) {
        settingDataStore.edit { preference ->
            preference[USER_EXPIRED] = time
        }
    }

    suspend fun clear() {
        settingDataStore.edit {
            it.clear()
        }
    }
}