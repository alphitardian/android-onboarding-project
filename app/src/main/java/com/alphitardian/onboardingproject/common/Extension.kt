package com.alphitardian.onboardingproject.common

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object Extension {
    fun String?.formatDate(): String? {
        val localDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        return DateTimeFormatter.ofPattern("dd MMM yy").format(localDate)
    }

    fun String.toEpochTime(): Long {
        val localDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        return localDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli() / 1000
    }

    fun String?.formatCategory(): String? {
        return this?.split("/")?.get(1)?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    fun Throwable.handleErrorCode(): Int {
        return if (this is HttpException) {
            val errorMessage = this.localizedMessage ?: ""
            val errorCode = errorMessage.split(" ")[1]

            when (ErrorState.fromRawValue(errorCode.toInt())) {
                ErrorState.ERROR_400 -> errorCode.toInt()
                ErrorState.ERROR_401 -> errorCode.toInt()
                ErrorState.ERROR_422 -> errorCode.toInt()
                ErrorState.ERROR_UNKNOWN -> 0
                else -> 0
            }
        } else {
            0
        }
    }
}