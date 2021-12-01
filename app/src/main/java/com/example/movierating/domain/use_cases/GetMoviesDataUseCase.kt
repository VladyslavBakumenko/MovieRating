package com.example.movierating.domain.use_cases

import com.example.movierating.domain.FormattedTotalMovieData
import com.example.movierating.domain.MovieRatingRepositiry

class GetMoviesDataUseCase(private val movieRatingRepositiry: MovieRatingRepositiry) {

    fun getMoviesDataUseCase(): FormattedTotalMovieData {
        return movieRatingRepositiry.getMoviesDataUseCase()
    }
}