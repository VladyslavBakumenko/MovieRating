package com.example.movierating.presentation.ui.activitys.loginActivity

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.api.ApiFactory
import com.example.movierating.data.internet.session.requests.AuthRequest
import com.example.movierating.data.internet.session.requests.RequestToken
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

    private val coroutineScopeIo = CoroutineScope(Dispatchers.IO)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _userFound = MutableLiveData<Boolean>()
    val userFound: LiveData<Boolean>
        get() = _userFound


    fun checkUserToLogin(): Boolean {
        return sharedPreferences.getString(SESSION_ID, EMPTY_FIELD) != EMPTY_FIELD
    }

    fun loginUser(userName: String, password: String) {
        if(setInputErrors(userName, password)) {
            coroutineScopeIo.launch {
                val requestToken = ApiFactory.movieApi.getRequestToken().body()
                    ?.requestToken

                val requestTokenForCreateNewSession = ApiFactory
                    .movieApi
                    .createSessionWithLogin(
                        request = AuthRequest(userName, password, requestToken)
                    ).body()?.requestToken

                val sessionId = ApiFactory.movieApi.createNewSession(
                    token = RequestToken(requestTokenForCreateNewSession)
                ).body()?.sessionId
                saveUserData(requestToken, requestTokenForCreateNewSession, sessionId)

                if(sessionId != null) _userFound.postValue(true)
            }
        }
    }

    private fun saveUserData(
        requestToken: String?,
        requestTokenForCreateNewSession: String?,
        sessionId: String?
    ) {

//        val manager = SharedPreferencesManager() as ISharedPreferencesManager
//
//        with(manager) {
//            putString(SharedPreferencesManager.REQUEST_TOKEN, requestToken)
//        }
//


        with(sharedPreferences) {

            edit().putString(REQUEST_TOKEN, requestToken).commit()
            edit().putString(REQUEST_TOKEN_FOR_CREATE_SESSION, requestTokenForCreateNewSession)
                .commit()
            edit().putString(SESSION_ID, sessionId).commit()
        }
    }

    private fun setInputErrors(userName: String, password: String): Boolean {
        var counter = 1

        if(!checkEmailOnValid(userName)) {
            _errorInputEMail.postValue(true)
            counter--
        }
        if(!checkPasswordOnValid(password)) {
            _errorInputPassword.postValue(true)
            counter--
        }
        return counter == 1
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
        private const val EMPTY_FIELD = "emptyField"
    }
}
