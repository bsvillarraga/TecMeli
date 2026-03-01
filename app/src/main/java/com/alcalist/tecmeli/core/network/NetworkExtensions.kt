package com.alcalist.tecmeli.core.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    transform: (T) -> R
): Result<R> = withContext(Dispatchers.IO) {
    try {
        val response = call()
        val body = response.body()
        Log.e("safeApiCall", "Response: $body")

        if (response.isSuccessful && body != null) {
            Result.success(transform(body))
        } else {
            val errorMsg = "Error: ${response.code()} ${response.message()}"
            Result.failure(Exception(errorMsg))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
