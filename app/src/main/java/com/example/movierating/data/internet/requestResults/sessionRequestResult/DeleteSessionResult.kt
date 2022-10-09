package com.example.movierating.data.internet.requestResults.sessionRequestResult

import com.google.gson.annotations.SerializedName

data class DeleteSessionResult(
    @SerializedName("success")
    val success: Boolean
)