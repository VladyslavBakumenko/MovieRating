package com.example.movierating.presentation.ui.login_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase
import com.example.movierating.data.repositorys_impl.MovieRatingRepositoryImpl
import com.example.movierating.domain.MovieRatingRepositiry
import com.example.movierating.domain.use_cases.CheckEmailOnValidUseCase
import com.example.movierating.domain.use_cases.CheckPasswordOnValidUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBase.getInstance(application)
    private val corutineScope = CoroutineScope(Dispatchers.IO)

    private val checkPasswordOnValidUseCase = CheckPasswordOnValidUseCase(MovieRatingRepositoryImpl)
    private val checkEmailOnValidUseCase = CheckEmailOnValidUseCase(MovieRatingRepositoryImpl)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _userFound= MutableLiveData<Boolean>()
    val userFound: LiveData<Boolean>
        get() = _userFound

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword



    fun checkUserInDatabase(eMail: String, password: String) {

        if (validateInput(eMail, password)) {
            corutineScope.launch {

                val usersArray = db.usersDatabaseDao().getOllUsersRegistrationInfo()
                for(i in usersArray) {
                    if(i.eMail == eMail) {
                        if(i.password == password) {
                            _userFound.postValue(true)
                        }
                    }
                }
            }
        }
    }


    private fun setEMileError(eMail: String): Boolean {
        var result = false
        if (checkEmailOnValidUseCase.checkEmailOnValid(eMail)) {
            result = true
        } else {
            _errorInputEMail.value = true
        }
        return result
    }

    private fun setPasswordError(password: String): Boolean {
        var result = false
        if (checkPasswordOnValidUseCase.checkPasswordOnValid(password)) {
            result = true
        } else {
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
