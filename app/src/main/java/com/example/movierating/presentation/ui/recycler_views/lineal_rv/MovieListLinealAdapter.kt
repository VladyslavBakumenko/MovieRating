package com.example.movierating.presentation.ui.recycler_views.lineal_rv

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.data.internet.MovieResult

import com.squareup.picasso.Picasso

class MovieListLinealAdapter() : ListAdapter<MovieResult, MovieItemLinealViewHolder>(linealDiffCallback()) {
    var count: Int = 0
    var count1: Int = 0

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemLinealViewHolder {
        Log.d("onCreateViewHolder", count++.toString())
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.lineal_movie_item,
            parent,
            false
        )
        return MovieItemLinealViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieItemLinealViewHolder, position: Int) {
        Log.d("onBindViewHolder", count1++.toString())


        with(holder) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${getItem(position).backdropPath}")
                .into(holder.imageMovie)
            tvMovieDescription.text = getItem(position).overview
            tvMovieName.text = getItem(position).title
            tvMovieRelise.text = getItem(position).releaseDate.toString()
            tvMovieRate.text = "Rate: ${getItem(position).voteAverage.toString()}"

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(
                    getItem(position).title,
                    getItem(position).overview,
                    getItem(position).releaseDate,
                    getItem(position).voteAverage.toString(),
                    getItem(position).originalLanguage,
                    getItem(position).popularity.toString(),
                    "${getItem(position).posterPath}"
                )
            }
        }

    }


    interface OnMovieClickListener {
        fun onMovieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?
        )
    }
}



