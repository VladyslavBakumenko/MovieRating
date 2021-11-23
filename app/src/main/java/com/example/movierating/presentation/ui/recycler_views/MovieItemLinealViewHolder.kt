package com.example.movierating.presentation.ui.recycler_views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R

class MovieItemLinealViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val imageMovie = view.findViewById<ImageView>(R.id.image_movie_item_lineal)
    val tvMovieName = view.findViewById<TextView>(R.id.tv_movie_name_lineal)
    val tvMovieDescription = view.findViewById<TextView>(R.id.tv_movie_description_lineal)
    val tvMovieRate = view.findViewById<TextView>(R.id.tv_movie_rate_lineal)
    val tvMovieRelise = view.findViewById<TextView>(R.id.tv_movie_relise_lineal)
}