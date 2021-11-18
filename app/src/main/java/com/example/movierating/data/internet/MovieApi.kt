package com.example.movierating.data.internet

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/popular/")
    fun getMovie(
      //  @PATCH("category") category: String = "",
        @Query(QUERY_PARAM_API_KEY) api_key: String = "dde60d5a51c393a90552aecde67b7d4b",
        @Query(QUERY_PARAM_LANGUAGE) language: String = ENGLISH ,
        @Query(QUERY_PARAM_PAGE) page: Int
    ): Single<MoviePages>

    //fun getMovieList(){
    //    @PATCH("category" category: String),
   //////     @Query("api_key",  apiKey: String)
   // }

    companion object {
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_API_KEY = "api_key"

        private const val ENGLISH = "en-US"
    }
   ///Call<MoviePages>
}