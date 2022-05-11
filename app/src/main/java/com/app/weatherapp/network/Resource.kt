package com.app.weatherapp.network

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String? = "",
    val code: Int = 200,
) {
    companion object {
        fun <T> success(data: T, message: String? = ""): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String?, data: T? = null, code: Int = 200): Resource<T> {
            return Resource(Status.ERROR, data, message, code)
        }


        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
