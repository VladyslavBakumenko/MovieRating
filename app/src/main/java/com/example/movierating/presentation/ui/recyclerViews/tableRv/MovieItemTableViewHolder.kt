package com.example.movierating.presentation.ui.recyclerViews.tableRv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R

class MovieItemTableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageMovie = view.findViewById<ImageView>(R.id.film_image)
    val nameMovie = view.findViewById<TextView>(R.id.film_name)
    val movieCardView = view.findViewById<CardView>(R.id.movie_card_view)
}