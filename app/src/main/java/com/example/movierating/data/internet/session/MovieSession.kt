package com.example.movierating.data.internet.session

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieSession(
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null
)