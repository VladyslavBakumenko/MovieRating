package com.example.movierating.presentation.ui.recycler_views.table_rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R

class MovieItemTableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val firstImageMovie = view.findViewById<ImageView>(R.id.first_film_image)
    val firstNameMovie = view.findViewById<TextView>(R.id.first_film_name)
    val firstMovieCardView = view.findViewById<CardView>(R.id.first_movie_card_view)

    val secondImageMovie = view.findViewById<ImageView>(R.id.second_film_image)
    val secondNameMovie = view.findViewById<TextView>(R.id.second_film_name)
    val secondMovieCardView = view.findViewById<CardView>(R.id.second_movie_card_view)

    val thirdImageMovie = view.findViewById<ImageView>(R.id.third_film_image)
    val thirdNameMovie = view.findViewById<TextView>(R.id.third_film_name)
    val thirdMovieCardView = view.findViewById<CardView>(R.id.third_movie_card_view)

}