package com.example.movierating.domain

import com.example.movierating.domain.MovieRatingRepositiry

class CheckEmailOnValidUseCase(private val movieRateRepository: MovieRatingRepositiry) {

    fun checkEmailOnValid(eMail: String): Boolean {
        return movieRateRepository.checkEmailOnValid(eMail)
    }
}