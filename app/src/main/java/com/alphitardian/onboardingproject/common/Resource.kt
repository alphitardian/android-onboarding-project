package com.alphitardian.onboardingproject.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val state: ErrorState? = null,
) {
    class Success<T>(data: T, message: String? = null) : Resource<T>(data, message)
    class Error<T>(message: String? = null, data: T? = null, state: ErrorState? = null) :
        Resource<T>(data, message, state)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}
