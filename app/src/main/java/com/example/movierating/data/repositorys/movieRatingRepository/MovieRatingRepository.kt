package com.example.movierating.data.repositorys.movieRatingRepository

import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult

interface MovieRatingRepository {
    suspend fun loadData(page: Int) : List<MovieResult>?
}