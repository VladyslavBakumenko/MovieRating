package com.example.movierating.domain

interface MovieRatingRepositiry {
    fun checkEmailOnValid(eMail: String): Boolean
    fun checkPasswordOnValid(password: String): Boolean
    fun validateInput(eMail: String, password: String): Boolean

    companion object {
        const val RETURN_FALSE_IF_FIELDS_INVALID = -1
        const val RETURN_TRUE_IF_FIELDS_VALID = 1
    }
}
