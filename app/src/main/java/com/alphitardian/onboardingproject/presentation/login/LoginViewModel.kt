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
import com.alphitardian.onboardingproject.common.ErrorState
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
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

    var mutableErrorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = mutableErrorState

    private val datastore = PrefStore(context)

    init {
        viewModelScope.launch {
            datastore.clear()
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            runCatching {
                mutableLoginState.postValue(Resource.Loading())
                val body = LoginRequest(password = password.value, username = email.value)
                val result = loginUseCase(body)
                dataStoreTransaction(result)
                mutableLoginState.postValue(Resource.Success<TokenResponse>(data = result))
            }.getOrElse {
                val error = Resource.Error<TokenResponse>(error = it)
                mutableLoginState.postValue(error)
                handleError(response = error)
            }
        }
    }

    private fun handleError(response: Resource.Error<TokenResponse>) {
        if (response.error is HttpException) {
            val errorMessage = response.error.localizedMessage
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(Integer.parseInt(errorCode))) {
                ErrorState.ERROR_400 -> mutableErrorState.value = ErrorState.ERROR_400.code
                ErrorState.ERROR_401 -> mutableErrorState.value = ErrorState.ERROR_401.code
                ErrorState.ERROR_422 -> mutableErrorState.value = ErrorState.ERROR_422.code
                ErrorState.ERROR_UNKNOWN -> mutableErrorState.value = ErrorState.ERROR_UNKNOWN.code
            }
        } else {
            mutableErrorState.value = ErrorState.ERROR_UNKNOWN.code
        }
    }

    private fun dataStoreTransaction(response: TokenResponse) {
        val time = response.expiresTime
        val localDate = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
        val epochTime = localDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli() / 1000
        saveExpiredTime(epochTime)
        encryptToken(response.token)
    }

    private fun encryptToken(token: String) {
        viewModelScope.launch {
            val encrypt = KeystoreHelper.encrypt(token.toByteArray())
            val encryptedToken = Base64.encodeToString(encrypt["encrypted"], Base64.DEFAULT)
            val iv = Base64.encodeToString(encrypt["iv"], Base64.DEFAULT)

            saveToken(encryptedToken)
            saveTokenIV(iv)
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
}