package com.example.movierating.domain

import androidx.lifecycle.LiveData

class GetMovieListUseCase(private val movieRatingRepositiry: MovieRatingRepositiry) {

    fun getMovieList(): LiveData<List<MovieItem>> {
        return movieRatingRepositiry.getMovieList()
    }
}