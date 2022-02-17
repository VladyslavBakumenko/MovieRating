package com.example.movierating.data.internet.api

import com.example.movierating.data.internet.requestResults.moviesRequestResult.MoviePages
import com.example.movierating.data.internet.requestResults.sessionRequestResult.DeleteSessionResult
import com.example.movierating.data.internet.requests.AuthRequest
import com.example.movierating.data.internet.requestResults.sessionRequestResult.MovieToken
import com.example.movierating.data.internet.requests.RequestToken
import com.example.movierating.data.internet.requestResults.sessionRequestResult.SessionId
import com.example.movierating.data.internet.requests.SessionIdRequest
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {

    @GET("/3/movie/popular/")
    suspend fun getMovie(
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = ENGLISH,
        @Query(QUERY_PARAM_PAGE) page: Int
    ): Response<MoviePages>

    @GET("/3/authentication/token/new?")
    suspend fun getRequestToken(
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY
    ): Response<MovieToken>

    @POST("/3/authentication/token/validate_with_login")
    suspend fun createSessionWithLogin(
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY,
        @Body request: AuthRequest
    ): Response<MovieToken>

    @POST("/3/authentication/session/new")
    suspend fun createNewSession(
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY,
        @Body token: RequestToken
    ): Response<SessionId>

//    @DELETE("/3/authentication/session")
//    suspend fun deleteSession(
//        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY,
//        @Body sessionId: SessionIdRequest
//    ): DeleteSessionResult


    @HTTP(method = "DELETE", hasBody = true)
    suspend fun deleteSession(
        @Url url: String = URL_FOR_DELETE_SESSION,
        @Body sessionId: SessionIdRequest,
        @Query(QUERY_PARAM_API_KEY) api_key: String = API_KEY
    ): DeleteSessionResult


    companion object {
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val API_KEY = "dde60d5a51c393a90552aecde67b7d4b"
        private const val ENGLISH = "en-US"

        const val IMAGE_TMDB = "https://image.tmdb.org/t/p/w342"
        const val IMAGE_TMDB_BEST_QUALITY = "https://image.tmdb.org/t/p/w780"

        const val URL_FOR_DELETE_SESSION = "/3/authentication/session"
    }
}