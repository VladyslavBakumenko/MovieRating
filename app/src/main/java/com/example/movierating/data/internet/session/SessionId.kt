package com.example.movierating.data.internet.session

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionId(

    @SerializedName("session_id")
    @Expose
    val sessionId: String?
)
