package com.alphitardian.onboardingproject.common

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
object KeystoreHelper {
    private val KEYSTORE_NAME = "AndroidKeyStore"
    private val KEY_ALIAS = "KeyAlias"

    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_NAME)
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setRandomizedEncryptionRequired(true)
        .build()

    fun encrypt(data: ByteArray): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()
        try {
            val keyStore = KeyStore.getInstance(KEYSTORE_NAME)
            keyStore.load(null)

            val secretKeyEntry =
                keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val ivBytes = cipher.iv
            val encryptedBytes = cipher.doFinal(data)

            map["iv"] = ivBytes
            map["encrypted"] = encryptedBytes
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    fun decrypt(data: String, iv: String): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            val keyStore = KeyStore.getInstance(KEYSTORE_NAME)
            keyStore.load(null)

            val secretKeyEntry =
                keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            val encryptedBytes = Base64.decode(data, Base64.DEFAULT)
            val ivBytes = Base64.decode(iv, Base64.DEFAULT)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, ivBytes, 0, 12)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            decrypted = cipher.doFinal(encryptedBytes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return decrypted
    }
}