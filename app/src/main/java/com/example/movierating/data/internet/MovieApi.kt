package com.example.movierating.data.internet

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/popular/")
    fun getMovie(
      //  @PATCH("category") category: String = "",
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = ENGLISH ,
        @Query(QUERY_PARAM_PAGE) page: Int

    ): Single <MoviePages>



    companion object {
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val API_KEY = "dde60d5a51c393a90552aecde67b7d4b"

        private const val ENGLISH = "en-US"
        const val IMAGE_TMDB = "https://image.tmdb.org/t/p/w342"
        const val IMAGE_TMDB_POSTER = "https://image.tmdb.org/t/p/w780"
    }
}