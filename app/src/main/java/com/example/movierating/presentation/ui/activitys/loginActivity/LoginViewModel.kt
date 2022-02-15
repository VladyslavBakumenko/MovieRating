package com.example.movierating.presentation.ui.activitys.loginActivity

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.session.AuthRequest
import com.example.movierating.data.internet.session.RequestToken
import com.example.movierating.data.repositoriesImpl.UserRepository
import com.example.movierating.utils.checkEmailOnValid
import com.example.movierating.utils.checkPasswordOnValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    @Inject
    lateinit var userRepository: UserRepository
    private val coroutineScopeIo = CoroutineScope(Dispatchers.IO)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _userFound = MutableLiveData<String>()
    val userFound: LiveData<String>
        get() = _userFound


    fun checkUserToLogin(): Boolean {
        var result = true
        if (!sharedPreferences.getBoolean(USER_LOGIN, false)) {
            result = false
        }
        return result
    }

    fun loginUser(userName: String, password: String) {
        setInputErrors(userName, password)

        coroutineScopeIo.launch {
            val requestToken = ApiFactory.movieApi.getRequestToken().body()
                ?.requestToken.toString()

            val requestTokenForCreateNewSession = ApiFactory
                .movieApi
                .createSessionWithLogin(
                    request = AuthRequest(userName, password, requestToken)
                ).body()?.requestToken.toString()

            val sessionId = ApiFactory.movieApi.createNewSession(
                token = RequestToken(requestTokenForCreateNewSession)
            ).body()?.sessionId.toString()
            _userFound.postValue(sessionId)

            saveUserData(requestToken, requestTokenForCreateNewSession, sessionId)
        }
    }

    private fun saveUserData(
        requestToken: String,
        requestTokenForCreateNewSession: String,
        sessionId: String
    ) {
        with(sharedPreferences) {
            edit().putString(REQUEST_TOKEN, requestToken).commit()
            edit().putString(REQUEST_TOKEN_FOR_CREATE_SESSION, requestTokenForCreateNewSession)
                .commit()
            edit().putString(SESSION_ID, sessionId).commit()
            edit().putBoolean(USER_LOGIN, true).commit()
        }
    }

    private fun setInputErrors(userName: String, password: String) {
        if(!checkEmailOnValid(userName)) _errorInputEMail.postValue(true)
        if(!checkPasswordOnValid(password)) _errorInputPassword.postValue(true)
    }

    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    companion object {
        private const val REQUEST_TOKEN = "requestToken"
        private const val REQUEST_TOKEN_FOR_CREATE_SESSION = "tokenForCreateSession"
        private const val SESSION_ID = "sessionId"
        private const val USER_LOGIN = "userLogin"
    }
}
