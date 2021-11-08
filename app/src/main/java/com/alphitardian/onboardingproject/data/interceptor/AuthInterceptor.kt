package com.alphitardian.onboardingproject.data.interceptor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.BuildConfig
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.decrypt_token.DecryptTokenUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val decryptTokenUseCase: DecryptTokenUseCase,
) : Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        val datastore = PrefStore(context)
        var request = chain.request()

        runBlocking {
            delay(1000)

            val token = datastore.userToken.first().toString()
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