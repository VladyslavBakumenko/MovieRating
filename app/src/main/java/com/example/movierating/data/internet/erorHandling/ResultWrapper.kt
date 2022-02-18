package com.example.movierating.data.internet.erorHandling

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
    success: (response: T) -> Unit,
    error: (exception: Exception) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if (response is Response<*> && !response.isSuccessful) {
                throw  HttpException(response)
            }
            success(response)

        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> error(throwable)
                is HttpException -> error(throwable)
                else -> null
            }
        }
    }
}
