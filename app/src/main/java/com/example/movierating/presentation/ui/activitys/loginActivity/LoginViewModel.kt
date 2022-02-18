package com.example.movierating.presentation.ui.activitys.loginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.erorHandling.safeApiCall
import com.example.movierating.data.internet.requests.AuthRequest
import com.example.movierating.data.internet.requests.RequestToken
import com.example.movierating.data.repositorys.userRepository.UserRepositoryImpl
import com.example.movierating.data.sharedPreferencesManager.SharedPreferencesManager
import com.example.movierating.utils.checkEmailOnValid
import com.example.movierating.utils.checkPasswordOnValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

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

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError


    fun checkUserToLogin(): Boolean {
        return sharedPreferencesManager.getString(
            SharedPreferencesManager.SESSION_ID,
            SharedPreferencesManager.EMPTY_FIELD
        ) != SharedPreferencesManager.EMPTY_FIELD
    }

    fun loginUser(userName: String, password: String) {
        if (setInputErrors(userName, password)) getRequestToken(userName, password)
    }

    private fun getRequestToken(userName: String, password: String) {
        coroutineScopeIo.launch {
            safeApiCall({ userRepository.getRequestToken() },
                {
                    sharedPreferencesManager.putString(
                        SharedPreferencesManager.REQUEST_TOKEN,
                        it.toString()
                    )

                    createSessionWithLogin(userName, password, it?.requestToken.toString())
                }, {
                    _networkError.postValue(true)
                })
        }
    }

    private fun createSessionWithLogin(userName: String, password: String, requestToken: String) {
        coroutineScopeIo.launch {
            safeApiCall({
                userRepository.createSessionWithLogin(
                    AuthRequest(
                        userName,
                        password,
                        requestToken
                    )
                )
            }, {
                sharedPreferencesManager.putString(
                    SharedPreferencesManager.REQUEST_TOKEN_FOR_CREATE_SESSION, it.toString())

                createNewSession(it?.requestToken.toString())
            }, {
                _networkError.postValue(true)
            })
        }
    }

    private fun createNewSession(requestTokenForCreateNewSession: String?) {
        coroutineScopeIo.launch {
            safeApiCall({
                userRepository.createNewSession(
                    RequestToken(requestTokenForCreateNewSession)
                )
            },
                {
                    if(!it?.sessionId.isNullOrBlank())  {
                        sharedPreferencesManager
                            .putString(SharedPreferencesManager.SESSION_ID, it.toString())
                        _userFound.postValue(true)
                    }

                }, {
                    _networkError.postValue(true)
                })
        }
    }

/*    private fun saveUserData(
        requestToken: String?,
        requestTokenForCreateNewSession: String?,
        sessionId: String?
    ) {

        with(sharedPreferencesManager) {
            putString(SharedPreferencesManager.REQUEST_TOKEN, requestToken)
            putString(
                SharedPreferencesManager.REQUEST_TOKEN_FOR_CREATE_SESSION,
                requestTokenForCreateNewSession
            )
            putString(SharedPreferencesManager.SESSION_ID, sessionId)
        }
    }*/

    private fun setInputErrors(userName: String, password: String): Boolean {
        var counter = 1

        if (!checkEmailOnValid(userName)) {
            _errorInputEMail.postValue(true)
            counter--
        }
        if (!checkPasswordOnValid(password)) {
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
}
