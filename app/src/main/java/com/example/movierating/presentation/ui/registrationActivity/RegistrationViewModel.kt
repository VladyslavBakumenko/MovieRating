package com.example.movierating.presentation.ui.registrationActivity

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.database.UsersDatabase
import com.example.movierating.data.repositoriesImpl.UserRepositoryImpl
import com.example.movierating.utils.checkEmailOnValid
import com.example.movierating.utils.checkPasswordOnValid
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor (application: Application) : AndroidViewModel(application) {

    private val coroutineScopeIO = CoroutineScope(Dispatchers.IO)

    private val userRepository = UserRepositoryImpl()

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _userAddedSuccessfully = MutableLiveData<Boolean>()
    val userAddedSuccessfully: LiveData<Boolean>
        get() = _userAddedSuccessfully


    fun addUserToData(eMail: String, password: String) {

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
    }


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