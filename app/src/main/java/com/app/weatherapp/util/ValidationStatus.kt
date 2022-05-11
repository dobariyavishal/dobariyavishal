package com.app.weatherapp.util

import android.app.Activity
import com.app.weatherapp.R

enum class ValidationStatus {
    EMPTY_CITY_NAME,
    INTERNET_CONNECTION
}

object Validation {
    fun showMessageDialog(activity: Activity, validationStatus: ValidationStatus) {
        val message = getMessage(activity, validationStatus)
        if (message.isNotEmpty()) {
            activity.showMessage(message = message)
        }
    }

    private fun getMessage(activity: Activity, it: ValidationStatus): String {
        return when (it) {
            ValidationStatus.EMPTY_CITY_NAME -> activity.getString(R.string.please_enter_city_name)
            ValidationStatus.INTERNET_CONNECTION -> activity.getString(R.string.internet_not_connected)
        }
    }
}