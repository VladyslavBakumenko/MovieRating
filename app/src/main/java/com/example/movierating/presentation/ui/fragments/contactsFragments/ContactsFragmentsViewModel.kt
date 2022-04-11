package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.MyForegroundService
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsFragmentsViewModel @Inject constructor(private val application: Application) :
    ViewModel() {

    private val loadedContacts = mutableListOf<ContactInfo>()

    private val _contacts = MutableLiveData<List<ContactInfo>>()
    val contacts: LiveData<List<ContactInfo>>
        get() = _contacts

    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val contact =
                intent.
                getParcelableExtra<ContactInfo>(MyForegroundService.CONTACT_KEY) as ContactInfo
            loadedContacts.add(contact)
            _contacts.value = loadedContacts
        }
    }

    fun getContactsFromService() {
        application.registerReceiver(
            broadcastReceiver,
            IntentFilter(MyForegroundService.ACTION_CONTACTS)
        )
    }

    fun checkContactsPermission() {
        _permissionGranted.value = !(ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.WRITE_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun onCleared() {
        super.onCleared()
        application.unregisterReceiver(broadcastReceiver)
    }
}