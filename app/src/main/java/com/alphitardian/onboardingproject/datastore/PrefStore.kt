package com.alphitardian.onboardingproject.datastore

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefStore @Inject constructor(@ApplicationContext context: Context) {
}