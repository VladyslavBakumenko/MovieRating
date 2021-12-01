package com.example.movierating.domain.use_cases

import com.example.movierating.domain.MovieItem
import com.example.movierating.domain.MovieRatingRepositiry

class GetMovieTableListUseCase(private val movieRatingRepositiry: MovieRatingRepositiry) {

    fun getMovieTableList(): List<MovieItem> {
        return movieRatingRepositiry.getMovieTableList()
    }
}