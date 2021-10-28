package com.alphitardian.onboardingproject.domain.use_case.decrypt_token

import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.common.KeystoreHelper
import javax.inject.Inject

class DecryptTokenUseCase @Inject constructor(private val keystore: KeystoreHelper) {
    @RequiresApi(Build.VERSION_CODES.M)
    operator fun invoke(token: String, iv: String): String? {
        val result = keystore.decrypt(token, iv)
        return result?.decodeToString()
    }
}