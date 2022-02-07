package com.example.movierating.presentation.ui.activitys.loginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.repositoriesImpl.UserRepository
import com.example.movierating.utils.checkEmailOnValid
import com.example.movierating.utils.checkPasswordOnValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor () : ViewModel() {

    @Inject
    lateinit var userRepository: UserRepository
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _userFound = MutableLiveData<Boolean>()
    val userFound: LiveData<Boolean>
        get() = _userFound

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword


    fun checkUserInDatabase(eMail: String, password: String) {

        if (validateInput(eMail, password)) {
            coroutineScope.launch {

                val usersArray = userRepository.getUsersFromDatabase()
                for (i in usersArray) {
                    if (i.eMail == eMail) {
                        if (i.password == password) {
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

    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
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

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    private fun validateInput(eMail: String, password: String): Boolean {
        var result = false

        if (setEMileError(eMail) &&
            setPasswordError(password)
        ) {
            result = true
        }

        return result
    }
}
