package com.example.movierating.utils

fun checkEmailOnValid(userName: String): Boolean {
    var result = false
    if (userName.length < 32) {
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



