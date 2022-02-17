package com.example.movierating.data.internet.requestResults.moviesRequestResult

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviePages(
    @SerializedName("page")
    @Expose
    var page: Int? = null,

    @SerializedName("results")
    @Expose
    var results: List<MovieResult>? = null,

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null,

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
)

