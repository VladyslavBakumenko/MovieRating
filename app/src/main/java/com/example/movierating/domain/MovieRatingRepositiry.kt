package com.example.movierating.domain

import androidx.lifecycle.LiveData

interface MovieRatingRepositiry {
    fun checkEmailOnValid(eMail: String): Boolean
    fun checkPasswordOnValid(password: String): Boolean
    fun validateInput(eMail: String, password: String): Boolean
    fun addMovieItem(movieItem: MovieItem)
    fun getMovieList(): LiveData<List<MovieItem>>

    companion object {
        const val RETURN_FALSE_IF_FIELDS_INVALID = -1
        const val RETURN_TRUE_IF_FIELDS_VALID = 1
        const val TOTAL_MOVIE = 10000
        const val TOTAL_PAGES = 500
    }
}
