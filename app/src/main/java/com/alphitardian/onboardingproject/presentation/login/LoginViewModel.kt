package com.alphitardian.onboardingproject.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: UserLoginUseCase) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var mutableLoginState: MutableLiveData<Resource<TokenResponse>> = MutableLiveData()
    val loginState: LiveData<Resource<TokenResponse>> get() = mutableLoginState

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
}