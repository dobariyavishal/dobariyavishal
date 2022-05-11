package com.app.weatherapp.network

import android.content.Context
import android.content.Intent
import com.app.weatherapp.R
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

open class BaseDataSource @Inject constructor(private val mContext: Context) {
    open suspend fun <T> getResult(
        call: suspend () -> Response<T>,
    ): Resource<T> {
        try {
            val response = call()
            if (response.code() == 502) {
                return Resource.error("", code = response.code())
            } else if (response.code() != 200) {
                return Resource.error(
                    mContext.getString(R.string.some_thing_wrong),
                    code = response.code()
                )
            } else if (response.body() != null) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        return Resource.success(it,"")
                    }
                } else {
                    val body = response.body()
                    if (body != null) {
                        return Resource.error(
                            mContext.getString(R.string.some_thing_wrong),
                            code = response.code()
                        )
                    }
                }
            }
        } catch (e: Exception) {
            return Resource.error("Error => ${e.message} ?: $e")
        }
        return Resource.error(mContext.getString(R.string.internet_not_connected))
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }
}