package com.example.movierating.domain

import androidx.lifecycle.LiveData

interface MovieRatingRepositiry {
    fun checkEmailOnValid(eMail: String): Boolean
    fun checkPasswordOnValid(password: String): Boolean
    fun validateInput(eMail: String, password: String): Boolean
    fun getMovieList(): LiveData<List<MovieItem>>
    fun getMoviesDataUseCase(): FormattedTotalMovieData

    companion object {
        const val RETURN_FALSE_IF_FIELDS_INVALID = -1
        const val RETURN_TRUE_IF_FIELDS_VALID = 1
        const val TOTAL_MOVIE = 10000
        const val TOTAL_PAGES = 500
        const val LOAD_PAGES = 30
    }
}
