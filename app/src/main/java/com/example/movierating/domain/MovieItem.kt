package com.example.movierating.domain

import android.widget.ImageView

data class MovieItem(
    val id: Int,
    val name: String,
    val description: String,
    val releaseDate: String,
    val rate: Double,
    val originalLanguage: String
)



