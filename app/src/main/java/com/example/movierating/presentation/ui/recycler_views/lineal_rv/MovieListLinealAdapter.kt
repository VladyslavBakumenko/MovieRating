package com.example.movierating.presentation.ui.recycler_views.lineal_rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.data.internet.MovieResult

import com.squareup.picasso.Picasso

class MovieListLinealAdapter() :
    RecyclerView.Adapter<MovieItemLinealViewHolder>() {
    var count: Int = 0
    var count1: Int = 0
    var movieDataList: List<MovieResult> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
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


    override fun onBindViewHolder(holder: MovieItemLinealViewHolder, position: Int) {
        Log.d("onBindViewHolder", count1++.toString())


        with(holder) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${movieDataList[position].backdropPath}")
                .into(holder.imageMovie)
            tvMovieDescription.text = movieDataList[position].overview
            tvMovieName.text = movieDataList[position].title
            tvMovieRelise.text = movieDataList[position].releaseDate.toString()
            tvMovieRate.text = "Rate: ${movieDataList[position].voteAverage.toString()}"

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(
                    movieDataList[position].title,
                    movieDataList[position].overview,
                    movieDataList[position].releaseDate,
                    movieDataList[position].voteAverage.toString(),
                    movieDataList[position].originalLanguage,
                    movieDataList[position].popularity.toString(),
                    "${movieDataList[position].posterPath}"
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return movieDataList.size
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



