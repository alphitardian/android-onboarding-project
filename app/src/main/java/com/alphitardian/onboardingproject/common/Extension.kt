package com.alphitardian.onboardingproject.common

import android.os.Build
import androidx.annotation.RequiresApi
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.ChannelResponse
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Extension {
    @RequiresApi(Build.VERSION_CODES.O)
    fun String?.formatDate(): String? {
        val localDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        return DateTimeFormatter.ofPattern("dd MMM yy").format(localDate)
    }

    fun ChannelResponse?.formatCategory(): String? {
        return  this?.name?.split("/")?.get(1)?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    fun Throwable.handleErrorCode() : Int {
        return if (this is HttpException) {
            val errorMessage = this.localizedMessage
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(errorCode.toInt())) {
                ErrorState.ERROR_401 -> errorCode.toInt()
                ErrorState.ERROR_UNKNOWN -> 0
                else -> 0
            }
        } else {
            0
        }
    }
}