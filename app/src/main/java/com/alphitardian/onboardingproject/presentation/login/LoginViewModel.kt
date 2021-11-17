package com.alphitardian.onboardingproject.presentation.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.common.Extension.handleErrorCode
import com.alphitardian.onboardingproject.common.Extension.toEpochTime
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.ErrorResponse
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase,
    private val encryptTokenUseCase: EncryptTokenUseCase,
    private val datastore: PrefStore,
) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

    init {
        viewModelScope.launch {
            datastore.clear()
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            runCatching {
                mutableLoginState.postValue(Resource.Loading())
                val body = LoginRequest(password = password.value, username = email.value)
                val result = loginUseCase(body)
                dataStoreTransaction(result)
                mutableLoginState.postValue(Resource.Success<TokenResponse>(data = result))
                EspressoIdlingResource.decrement()
            }.getOrElse {
                val error = Resource.Error<TokenResponse>(error = it, code = it.handleErrorCode())
                mutableLoginState.postValue(error)
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun handleFieldValidation(error: Throwable?): ErrorResponse? {
        if (error is HttpException) {
            val errorBody = error.response()?.errorBody()?.string()
            val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody.toString())
            return errorResponse
        }
        return null
    }

    private fun dataStoreTransaction(response: TokenResponse) {
        val time = response.expiresTime.toEpochTime()
        saveExpiredTime(time)
        encryptToken(response.token)
    }

    private fun encryptToken(token: String) {
        viewModelScope.launch {
            val encryptedMap = encryptTokenUseCase(token)
            encryptedMap["token"]?.let { saveToken(it) }
            encryptedMap["iv"]?.let { saveTokenIV(it) }
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