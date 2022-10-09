package com.example.movierating.data.internet.requests

import com.google.gson.annotations.SerializedName

data class AuthRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("request_token")
    val token: String?

)