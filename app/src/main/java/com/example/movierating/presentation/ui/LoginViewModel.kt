package com.example.movierating.presentation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.MovieRatingRepositoryImpl
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase
import com.example.movierating.domain.CheckEmailOnValidUseCase
import com.example.movierating.domain.CheckPasswordOnValidUseCase
import com.example.movierating.domain.MovieRatingRepositiry

class LoginViewModel(application: Application) : AndroidViewModel(application) {

   private val db = AppDataBase.getInstance(application)

    private val checkPasswordOnValidUseCase = CheckPasswordOnValidUseCase(MovieRatingRepositoryImpl)
    private val checkEmailOnValidUseCase = CheckEmailOnValidUseCase(MovieRatingRepositoryImpl)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword


    fun checkUserInDatabase(eMail: String, password: String): Boolean {
        var result = false
        if(validateInput(eMail, password)) {
            val usersArray = getUsers()
            usersArray.forEachIndexed { index, usersArray ->
                if (usersArray.eMail == eMail) {
                    if (usersArray.password == password) {
                        result = true
                    }
                }
            }
        }
        return result
    }


    private fun getUsers(): Array<UsersDatabase> {
        return db.usersDataBaseDao().getOllUsersRegistrationInfo()
    }



    private fun setEMileError(eMail: String): Boolean {
        var result = false
        if(checkEmailOnValidUseCase.checkEmailOnValid(eMail)) {
            result = true
        }
        else {
            _errorInputEMail.value = true
        }
        return result
    }

    private fun setPasswordError(password: String): Boolean {
        var result = false
        if(checkPasswordOnValidUseCase.checkPasswordOnValid(password)) {
            result = true
        }
        else {
            _errorInputPassword.value = true
        }
        return result
    }

    private fun validateInput(eMail: String, password: String): Boolean {
        var result: Int = MovieRatingRepositiry.RETURN_FALSE_IF_FIELDS_INVALID

        if (setEMileError(eMail)) {
            result++
        }
        if (setPasswordError(password)) {
            result++
        }
        return result == MovieRatingRepositiry.RETURN_TRUE_IF_FIELDS_VALID
    }

    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }


}
