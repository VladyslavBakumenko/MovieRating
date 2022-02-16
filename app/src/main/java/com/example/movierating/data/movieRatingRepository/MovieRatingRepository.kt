package com.example.movierating.data.movieRatingRepository

import com.example.movierating.data.internet.movies.MovieResult

interface MovieRatingRepository {
    suspend fun loadData(page: Int) : List<MovieResult>?
}