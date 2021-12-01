package com.example.movierating.domain.use_cases

import com.example.movierating.domain.MovieItem
import com.example.movierating.domain.MovieRatingRepositiry

class GetMovieListLinealUseCase(private val movieRatingRepositiry: MovieRatingRepositiry) {

    fun getMovieLinealList(): List<MovieItem> {
        return movieRatingRepositiry.getMovieLinealList()
    }
}