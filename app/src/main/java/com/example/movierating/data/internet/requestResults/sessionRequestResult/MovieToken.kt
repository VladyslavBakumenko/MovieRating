package com.example.movierating.data.internet.requestResults.sessionRequestResult

import com.google.gson.annotations.SerializedName

data class MovieToken(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("expires_at")
    var expiresAt: String? = null,

    @SerializedName("request_token")
    var requestToken: String? = null
)