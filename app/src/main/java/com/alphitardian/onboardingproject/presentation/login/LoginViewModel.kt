package com.alphitardian.onboardingproject.presentation.login

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.KeystoreHelper
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

    private val datastore = PrefStore(context)

    @RequiresApi(Build.VERSION_CODES.O)
    fun loginUser() {
        viewModelScope.launch {
            runCatching {
                mutableLoginState.postValue(Resource.Loading())
                val body = LoginRequest(password = password.value, username = email.value)
                val result = loginUseCase(body)
                mutableLoginState.postValue(Resource.Success<TokenResponse>(data = result))
            }.getOrElse {
                val error = Resource.Error<TokenResponse>(error = it)
                mutableLoginState.postValue(error)
            }
        }
    }

    private fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveToken(token)
        }
    }

    private fun saveTokenIV(iv: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveTokenInitializationVector(iv)
        }
    }

    private fun saveExpiredTime(time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveExpiredTime(time)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun encryptToken(token: String) {
        viewModelScope.launch {
            KeystoreHelper.keyGenerator.init(KeystoreHelper.keyGenParameterSpec)
            KeystoreHelper.keyGenerator.generateKey()

            val encrypt = KeystoreHelper.encrypt(token.toByteArray())
            val encryptedToken = Base64.encodeToString(encrypt["encrypted"], Base64.DEFAULT)
            val iv = Base64.encodeToString(encrypt["iv"], Base64.DEFAULT)

            saveToken(encryptedToken)
            saveTokenIV(iv)
        }
    }
}