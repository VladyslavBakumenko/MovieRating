package com.example.movierating.domain.use_cases

import com.example.movierating.domain.MovieRatingRepositiry

class CheckPasswordOnValidUseCase(private val filmRateRepository: MovieRatingRepositiry) {

    fun checkPasswordOnValid(password: String): Boolean {
        return filmRateRepository.checkPasswordOnValid(password)
    }
}