package com.example.movierating.presentation.ui.activitys.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.api.ApiFactory
import com.example.movierating.data.internet.requests.SessionIdRequest
import com.example.movierating.data.sharedPreferencesManager.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _unLoginSuccess = MutableLiveData<Boolean>()
    val unLoginSuccess: LiveData<Boolean>
        get() = _unLoginSuccess

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    fun unLoginUser() {
        coroutineScope.launch {

            val sessionId = sharedPreferencesManager
                .getString(
                    SharedPreferencesManager.SESSION_ID,
                    SharedPreferencesManager.EMPTY_FIELD
                )
            ApiFactory.movieApi.deleteSession(sessionId = SessionIdRequest(sessionId))

            removeUserData()
            _unLoginSuccess.postValue(true)
        }
    }

    private fun removeUserData() {
        with(sharedPreferencesManager) {
            remove(SharedPreferencesManager.SESSION_ID)
            remove(SharedPreferencesManager.REQUEST_TOKEN)
            remove(SharedPreferencesManager.REQUEST_TOKEN_FOR_CREATE_SESSION)
        }
    }
}