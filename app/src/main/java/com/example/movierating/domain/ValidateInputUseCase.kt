package com.example.movierating.domain

import com.example.movierating.domain.MovieRatingRepositiry

class ValidateInputUseCase(private val filmRateRepository: MovieRatingRepositiry) {

    fun validateInput(eMail: String, password: String): Boolean {
        return filmRateRepository.validateInput(eMail, password)
    }

}