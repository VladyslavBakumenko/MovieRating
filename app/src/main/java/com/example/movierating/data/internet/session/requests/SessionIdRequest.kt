package com.example.movierating.data.internet.session.requests

import com.google.gson.annotations.SerializedName

data class SessionIdRequest(
    @SerializedName("session_id")
    val sessionId: String?
)