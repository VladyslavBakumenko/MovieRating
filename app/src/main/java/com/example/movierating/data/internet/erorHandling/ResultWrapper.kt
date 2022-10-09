package com.example.movierating.data.internet.erorHandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private val coroutineScopeIo = CoroutineScope(Dispatchers.IO)
private val coroutineScopeUi = CoroutineScope(Dispatchers.Main)

fun <T> safeApiCall(
    apiCall: suspend () -> T,
    success: (response: T) -> Unit,
    error: (exception: Exception) -> Unit
) {
    coroutineScopeIo.launch {
        try {
            val response = apiCall.invoke()
            if (response is Response<*> && !response.isSuccessful) {
                throw  HttpException(response)
            }
            coroutineScopeUi.launch {
                success(response)
            }
        } catch (throwable: Throwable) {
            coroutineScopeUi.launch {
                when (throwable) {
                    is IOException -> error(throwable)
                    is HttpException -> error(throwable)
                    else -> null
                }
            }
        }
    }


}
