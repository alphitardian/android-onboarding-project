package com.alphitardian.onboardingproject.data.interceptor

import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.decrypt_token.DecryptTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val datastore: PrefStore,
    private val decryptTokenUseCase: DecryptTokenUseCase,
) : Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        runBlocking {
            val token = datastore.userToken.firstOrNull().toString()
            val iv = datastore.tokenInitializationVector.first().toString()
            if (token.isNotEmpty() && iv.isNotEmpty()) {
                val decryptToken = decryptTokenUseCase(token, iv)
                decryptToken?.let {
                    request = request.newBuilder().addHeader("Authorization", "Bearer $it").build()
                }
            }
        }

        return chain.proceed(request)
    }
}