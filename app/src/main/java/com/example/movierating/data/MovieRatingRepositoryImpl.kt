package com.example.movierating.data


import com.example.movierating.domain.MovieRatingRepositiry

object MovieRatingRepositoryImpl : MovieRatingRepositiry {


    override fun checkEmailOnValid(eMail: String): Boolean {
        val validEMailAddress = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        var result = false
        if (validEMailAddress.matches(eMail)) {
            result = true
        }
        return result
    }

    override fun checkPasswordOnValid(password: String): Boolean {
        var result = false
        if (password.length in 4..20) {
            result = true
        }
        return result
    }

    override fun validateInput(eMail: String, password: String): Boolean {
        var result: Int = MovieRatingRepositiry.RETURN_FALSE_IF_FIELDS_INVALID

        if (checkEmailOnValid(eMail)) {
            result++
        }
        if (checkPasswordOnValid(password)) {
            result++
        }
        return result == MovieRatingRepositiry.RETURN_TRUE_IF_FIELDS_VALID
    }
}