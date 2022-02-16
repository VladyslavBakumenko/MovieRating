package com.example.movierating.presentation.ui.activitys.registrationActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.utils.checkEmailOnValid
import com.example.movierating.utils.checkPasswordOnValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    private val coroutineScopeIO = CoroutineScope(Dispatchers.IO)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _userAddedSuccessfully = MutableLiveData<Boolean>()
    val userAddedSuccessfully: LiveData<Boolean>
        get() = _userAddedSuccessfully


/*    fun addUserToData(eMail: String, password: String) {

        val fieldsValid = validateInput(eMail, password)
        if (fieldsValid) {

            val user = UsersDatabase(eMail, password)
            coroutineScopeIO.launch {
                try {
                    userRepository.addUserToDatabase(user)
                    _userAddedSuccessfully.postValue(true)

                } catch (e: SQLiteConstraintException) {
                    _userAddedSuccessfully.postValue(false)
                }
            }
        }
    }*/


    private fun setEMileError(eMail: String): Boolean {
        var result = false
        if (checkEmailOnValid(eMail)) result = true
        else _errorInputEMail.value = true

        return result
    }

    private fun setPasswordError(password: String): Boolean {
        var result = false
        if (checkPasswordOnValid(password)) result = true
        else _errorInputPassword.value = true

        return result
    }


    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }


    private fun validateInput(eMail: String, password: String): Boolean {
        var result = false
        if (setEMileError(eMail)
            && setPasswordError(password)
        ) result = true

        return result
    }

}