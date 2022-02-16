package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.api.ApiFactory
import com.example.movierating.data.internet.api.MovieApi
import com.example.movierating.data.internet.session.requests.SessionIdRequest
import com.example.movierating.data.sharedPreferencesManager.ISharedPreferencesManager

import com.example.movierating.data.sharedPreferencesManager.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _unLoginSuccess = MutableLiveData<Boolean>()
    val unLoginSuccess: LiveData<Boolean>
        get() = _unLoginSuccess

    fun unLoginUser() {

        val sessionId = sharedPreferences
            .getString(SharedPreferencesManager.SESSION_ID, SharedPreferencesManager.EMPTY_FIELD)

        coroutineScope.launch {
            _unLoginSuccess.postValue(
                ApiFactory.movieApi.deleteSession(
                    MovieApi.URL_FOR_DELETE_SESSION,
                    SessionIdRequest(sessionId)
                ).success
            )
        }
    }

    fun removeUserData() {
        val sharedPreferencesManager =
            SharedPreferencesManager(sharedPreferences) as ISharedPreferencesManager

        sharedPreferencesManager.remove(SharedPreferencesManager.SESSION_ID)
    }
}