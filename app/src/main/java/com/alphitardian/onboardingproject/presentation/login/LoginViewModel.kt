package com.alphitardian.onboardingproject.presentation.login

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.ErrorResponse
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase,
    private val encryptTokenUseCase: EncryptTokenUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

    private val datastore = PrefStore(context)

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
            }.getOrElse {
                val error = Resource.Error<TokenResponse>(error = it)
                handleError(response = error)
            }
            EspressoIdlingResource.decrement()
        }
    }

    private fun handleError(response: Resource.Error<TokenResponse>) {
        if (response.error is HttpException) {
            val errorMessage = response.error.localizedMessage
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(Integer.parseInt(errorCode))) {
                ErrorState.ERROR_400 -> mutableLoginState.postValue(Resource.Error(code = ErrorState.ERROR_400.code))
                ErrorState.ERROR_401 -> mutableLoginState.postValue(Resource.Error(code = ErrorState.ERROR_401.code))
                ErrorState.ERROR_422 -> mutableLoginState.postValue(Resource.Error(code = ErrorState.ERROR_422.code, error = response.error))
                ErrorState.ERROR_UNKNOWN -> mutableLoginState.postValue(Resource.Error(code = ErrorState.ERROR_UNKNOWN.code))
            }
        } else {
            mutableLoginState.postValue(Resource.Error(code = ErrorState.ERROR_UNKNOWN.code))
        }
    }

    fun handleFieldValidation(error: Throwable?) : ErrorResponse? {
        if (error is HttpException) {
            val errorBody = error.response()?.errorBody()?.string()
            val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody.toString())
            return errorResponse
        }
        return null
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