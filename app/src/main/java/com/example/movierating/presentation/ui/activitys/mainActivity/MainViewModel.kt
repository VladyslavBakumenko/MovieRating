package com.example.movierating.presentation.ui.activitys.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.erorHandling.safeApiCall
import com.example.movierating.data.internet.requests.SessionIdRequest
import com.example.movierating.data.repositorys.userRepository.UserRepositoryImpl
import com.example.movierating.data.sharedPreferencesManager.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _unLoginSuccess = MutableLiveData<Boolean>()
    val unLoginSuccess: LiveData<Boolean>
        get() = _unLoginSuccess

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError


    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    fun unLoginUser() {
        coroutineScope.launch {

            safeApiCall({ userRepository.deleteSession(SessionIdRequest(getSessionId())) },
                {
                    _unLoginSuccess.postValue(true)
                }, {
                    _networkError.postValue(true)
                })

            removeUserData()
            _unLoginSuccess.postValue(true)
        }
    }


    private fun getSessionId(): String? {
        return sharedPreferencesManager
           .getString(
               SharedPreferencesManager.SESSION_ID,
               SharedPreferencesManager.EMPTY_FIELD
           )
    }

    private fun removeUserData() {
        with(sharedPreferencesManager) {
            remove(SharedPreferencesManager.SESSION_ID)
            remove(SharedPreferencesManager.REQUEST_TOKEN)
            remove(SharedPreferencesManager.REQUEST_TOKEN_FOR_CREATE_SESSION)
        }
    }
}