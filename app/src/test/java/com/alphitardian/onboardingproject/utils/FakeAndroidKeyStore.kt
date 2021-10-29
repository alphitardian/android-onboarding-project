package com.alphitardian.onboardingproject.utils

import java.io.InputStream
import java.io.OutputStream
import java.security.*
import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.KeyGeneratorSpi
import javax.crypto.SecretKey

object FakeAndroidKeyStore {
    val setup by lazy {
        Security.addProvider(object : Provider("AndroidKeyStore", 1.0, "") {
            init {
                put("KeyStore.AndroidKeyStore", FakeKeyStore::class.java.name)
                put("KeyGenerator.AES", FakeAesKeyGenerator::class.java.name)
            }
        })
    }

    class FakeKeyStore : KeyStoreSpi() {

        private val wrapped = KeyStore.getInstance(KeyStore.getDefaultType())

        override fun engineGetKey(p0: String?, p1: CharArray?): Key = wrapped.getKey(p0, p1)

        override fun engineGetCertificateChain(p0: String?): Array<Certificate> =
            wrapped.getCertificateChain(p0)

        override fun engineGetCertificate(p0: String?): Certificate = wrapped.getCertificate(p0)

        override fun engineGetCreationDate(p0: String?): Date = wrapped.getCreationDate(p0)

        override fun engineSetKeyEntry(
            p0: String?,
            p1: Key?,
            p2: CharArray?,
            p3: Array<out Certificate>?,
        ) = wrapped.setKeyEntry(p0, p1, p2, p3)

        override fun engineSetKeyEntry(p0: String?, p1: ByteArray?, p2: Array<out Certificate>?) =
            wrapped.setKeyEntry(p0, p1, p2)

        override fun engineSetCertificateEntry(p0: String?, p1: Certificate?) =
            wrapped.setCertificateEntry(p0, p1)

        override fun engineDeleteEntry(p0: String?) = wrapped.deleteEntry(p0)

        override fun engineAliases(): Enumeration<String> = wrapped.aliases()

        override fun engineContainsAlias(p0: String?): Boolean = wrapped.containsAlias(p0)

        override fun engineSize(): Int = wrapped.size()

        override fun engineIsKeyEntry(p0: String?): Boolean = wrapped.isKeyEntry(p0)

        override fun engineIsCertificateEntry(p0: String?): Boolean = wrapped.isCertificateEntry(p0)

        override fun engineGetCertificateAlias(p0: Certificate?): String =
            wrapped.getCertificateAlias(p0)

        override fun engineStore(p0: OutputStream?, p1: CharArray?) = wrapped.store(p0, p1)

        override fun engineLoad(p0: InputStream?, p1: CharArray?) = wrapped.load(p0, p1)

    }

    class FakeAesKeyGenerator : KeyGeneratorSpi() {
        private val wrapped = KeyGenerator.getInstance("AES")

        override fun engineInit(p0: SecureRandom?) = Unit

        override fun engineInit(p0: AlgorithmParameterSpec?, p1: SecureRandom?) = Unit

        override fun engineInit(p0: Int, p1: SecureRandom?) = Unit

        override fun engineGenerateKey(): SecretKey = wrapped.generateKey()
    }
}