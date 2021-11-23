package com.example.movierating.domain

class AddMovieItemUseCase(private val movieRateRepository: MovieRatingRepositiry) {
    fun addMovieItem(movieItem: MovieItem)  {
        movieRateRepository.addMovieItem(movieItem)
    }
}