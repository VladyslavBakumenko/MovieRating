package com.example.movierating.data.internet.session

import com.google.gson.annotations.SerializedName

data class DeleteSessionResult(
    @SerializedName("success")
    val success: Boolean
)