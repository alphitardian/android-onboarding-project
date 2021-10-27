package com.alphitardian.onboardingproject.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: UserLoginUseCase) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

    var mutableErrorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = mutableErrorState

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
}