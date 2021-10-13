package com.alphitardian.onboardingproject.common

enum class ErrorState(val code: Int) {
    ERROR_400(400),
    ERROR_401(401),
    ERROR_422(422),
    ERROR_UNKNOWN(0);

    companion object {
        fun fromErrorCode(code: Int): ErrorState {
            values().forEach {
                when (it.code) {
                    code -> return it
                }
            }
            return ERROR_UNKNOWN
        }
    }
}