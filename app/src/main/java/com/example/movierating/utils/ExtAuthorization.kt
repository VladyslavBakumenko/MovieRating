package com.example.movierating.utils


fun checkEmailOnValid(eMail: String): Boolean {
    val validEMailAddress = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
    var result = false
    if (validEMailAddress.matches(eMail)) {
        result = true
    }
    return result
}

fun checkPasswordOnValid(password: String): Boolean {
    var result = false
    if (password.length in 4..20) {
        result = true
    }
    return result
}


