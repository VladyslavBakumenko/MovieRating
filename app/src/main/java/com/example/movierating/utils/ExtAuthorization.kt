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
    if (password.length <= 4) {
        result = true
    }
    return result

}



