package com.example.movierating.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactInfo(val name: String?, val number: String?, val id: String?): Parcelable