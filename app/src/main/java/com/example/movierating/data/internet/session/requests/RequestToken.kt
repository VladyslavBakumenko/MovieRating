package com.example.movierating.data.internet.session.requests

import com.google.gson.annotations.SerializedName

data class RequestToken(
    @SerializedName("request_token")
    val requestToken: String?
)