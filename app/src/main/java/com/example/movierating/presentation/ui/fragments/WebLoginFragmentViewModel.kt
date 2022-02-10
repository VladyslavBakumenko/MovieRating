package com.example.movierating.presentation.ui.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.ApiFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebLoginFragmentViewModel @Inject constructor (): ViewModel() {

    private var coroutineScope = CoroutineScope(Dispatchers.IO)


    private val _url = MutableLiveData<String>()
    val url: LiveData<String?>
        get() = _url


    fun getPermissionUrl() {
        coroutineScope.launch {
            val token = ApiFactory.movieApi.getRequestToken().body()?.requestToken

            _url.postValue(token.toString())
        }

    }



}