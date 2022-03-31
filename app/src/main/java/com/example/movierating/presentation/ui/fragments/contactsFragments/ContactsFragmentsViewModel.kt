package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsFragmentsViewModel @Inject constructor(private val application: Application)  :
    ViewModel() {

    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted


     fun checkContactsPermission() {
         _permissionGranted.value = !(ContextCompat.checkSelfPermission(
             application,
             android.Manifest.permission.READ_CONTACTS
         ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
             application,
             android.Manifest.permission.WRITE_CONTACTS
         ) != PackageManager.PERMISSION_GRANTED)
    }


}