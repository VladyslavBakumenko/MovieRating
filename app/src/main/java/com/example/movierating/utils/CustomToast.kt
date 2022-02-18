package com.example.movierating.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext


fun createToast(@ApplicationContext context: Context, message: String) {
    val toast = Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    )
    toast.show()
}
