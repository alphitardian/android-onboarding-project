package com.alphitardian.onboardingproject.domain.use_case.check_user_login_time

import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.datastore.PrefStore
import kotlinx.coroutines.flow.firstOrNull
import java.util.*
import javax.inject.Inject

class CheckUserLoginTimeUseCase @Inject constructor(private val datastore: PrefStore) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(getNewToken: () -> Unit): Boolean {
        val HOUR_IN_EPOCH_SECONDS = 3600

        val expiredTime = datastore.userExpired.firstOrNull()

        if (expiredTime != null) {
            if (expiredTime >= 0) {
                val endTime = expiredTime - Date().toInstant().epochSecond

                return when {
                    endTime > HOUR_IN_EPOCH_SECONDS -> true
                    endTime in 0 until HOUR_IN_EPOCH_SECONDS -> {
                        getNewToken()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}