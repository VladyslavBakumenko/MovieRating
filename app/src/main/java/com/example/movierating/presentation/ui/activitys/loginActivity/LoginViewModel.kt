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
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val _state = MutableLiveData<LoginActivityState>()
    val state: LiveData<LoginActivityState>
        get() = _state

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
        safeApiCall({ userRepository.getRequestToken() },
            {
                sharedPreferencesManager.putString(
                    SharedPreferencesManager.REQUEST_TOKEN,
                    it.toString()
                )
                createSessionWithLogin(userName, password, it?.requestToken.toString())
            }, {
                _state.value = NetworkError
            })

    }

    private fun createSessionWithLogin(userName: String, password: String, requestToken: String) {
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
                SharedPreferencesManager.REQUEST_TOKEN_FOR_CREATE_SESSION, it.toString()
            )

            createNewSession(it?.requestToken.toString())
        }, {
            _state.value = NetworkError
        })

    }

    private fun createNewSession(requestTokenForCreateNewSession: String?) {
        safeApiCall({
            userRepository.createNewSession(
                RequestToken(requestTokenForCreateNewSession)
            )
        },
            {
                if (!it?.sessionId.isNullOrBlank()) {
                    sharedPreferencesManager
                        .putString(SharedPreferencesManager.SESSION_ID, it.toString())
                   _state.postValue(UserFound(true))
                } else _state.postValue(UserFound(false))

            }, {
                _state.value = NetworkError
            })

    }


    private fun setInputErrors(userName: String, password: String): Boolean {
        var counter = 1

        if (!checkEmailOnValid(userName)) {
            _state.postValue(ErrorInputEMail(true))
            counter--
        }
        if (!checkPasswordOnValid(password)) {
            _state.postValue(ErrorInputPassword(true))
            counter--
        }
        return counter == 1
    }

    fun resetErrorInputEMail() {
        _state.value = ErrorInputEMail(false)
    }

    fun resetErrorInputPassword() {
        _state.value = ErrorInputPassword(false)
    }
}
