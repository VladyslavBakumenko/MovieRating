package com.example.movierating.domain

import androidx.lifecycle.LiveData

class GetMoviesDataUseCase(private val movieRatingRepositiry: MovieRatingRepositiry) {

    fun getMoviesDataUseCase(): FormattedTotalMovieData {
        return movieRatingRepositiry.getMoviesDataUseCase()
    }
}