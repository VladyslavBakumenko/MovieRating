package com.example.movierating.presentation.ui.login_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.repositorys_impl.MovieRatingRepositoryImpl
import com.example.movierating.domain.MovieRatingRepositiry
import com.example.movierating.domain.use_cases.CheckEmailOnValidUseCase
import com.example.movierating.domain.use_cases.CheckPasswordOnValidUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBase.getInstance(application)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


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
            coroutineScope.launch {

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
        if (checkEmailOnValid(eMail)) {
            result = true
        } else {
            _errorInputEMail.value = true
        }
        return result
    }

    private fun setPasswordError(password: String): Boolean {
        var result = false
        if (checkPasswordOnValid(password)) {
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

    private fun checkEmailOnValid(eMail: String): Boolean {
        val validEMailAddress = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        var result = false
        if (validEMailAddress.matches(eMail)) {
            result = true
        }
        return result
    }

    private fun checkPasswordOnValid(password: String): Boolean {
        var result = false
        if (password.length in 4..20) {
            result = true
        }
        return result
    }

    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }


}
