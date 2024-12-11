package com.dicoding.medvault.util

import com.dicoding.medvault.data.network.datamodel.MessageResponse
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

fun Exception.getErrorMessage(): String {
    return when (this) {
        is HttpException -> {
            try {
                val errorBody = response()?.errorBody()?.string()
                if (errorBody != null) {
                    val errorResponse = Gson().fromJson(errorBody, MessageResponse::class.java)
                    errorResponse.message
                } else {
                    "Unknown error"
                }
            } catch (e: Exception) {
                "Error parsing response: ${e.message}"
            }
        }

        is CancellationException -> {
            ""
        }

        else -> {
            this.message ?: this.stackTraceToString()
        }
    }
}