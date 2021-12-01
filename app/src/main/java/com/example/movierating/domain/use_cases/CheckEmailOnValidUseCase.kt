package com.example.movierating.domain.use_cases

import com.example.movierating.domain.MovieRatingRepositiry

class CheckEmailOnValidUseCase(private val movieRateRepository: MovieRatingRepositiry) {

    fun checkEmailOnValid(eMail: String): Boolean {
        return movieRateRepository.checkEmailOnValid(eMail)
    }
}