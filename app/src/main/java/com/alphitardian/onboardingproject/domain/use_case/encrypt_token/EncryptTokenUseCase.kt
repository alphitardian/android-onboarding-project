package com.alphitardian.onboardingproject.domain.use_case.encrypt_token

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.common.KeystoreHelper
import javax.inject.Inject

class EncryptTokenUseCase @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.M)
    operator fun invoke(token: String): HashMap<String, String> {
        val map = HashMap<String, String>()
        val encrypt = KeystoreHelper.encrypt(token.toByteArray())
        val encryptedToken = Base64.encodeToString(encrypt["encrypted"], Base64.DEFAULT)
        val iv = Base64.encodeToString(encrypt["iv"], Base64.DEFAULT)

        map["token"] = encryptedToken
        map["iv"] = iv

        return map
    }
}