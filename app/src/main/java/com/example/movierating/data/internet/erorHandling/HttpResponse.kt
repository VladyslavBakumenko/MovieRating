package com.example.movierating.data.internet.erorHandling

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}