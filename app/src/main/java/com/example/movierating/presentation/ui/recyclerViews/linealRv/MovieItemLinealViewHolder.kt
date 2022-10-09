package com.example.movierating.presentation.ui.recyclerViews.linealRv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.data.internet.api.MovieApi
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.databinding.LinealMovieItemBinding
import com.squareup.picasso.Picasso

class MovieItemLinealViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = LinealMovieItemBinding.bind(view)

    private val imageMovie =  binding.imageMovieItemLineal
    private val tvMovieName = binding.tvMovieNameLineal
    private val tvMovieDescription = binding.tvMovieDescriptionLineal
    private val tvMovieRate = binding.tvMovieRateLineal
    private val tvMovieRelise = binding.tvMovieReliseLineal

    fun bind(
        movieResult: MovieResult,
        onMovieClickListener: MovieListLinealAdapter.OnMovieClickListener?
    ) {
        with(movieResult) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${backdropPath}")
                .into(imageMovie)
            tvMovieDescription.text = overview
            tvMovieName.text = "$absoluteAdapterPosition $title"
            tvMovieRelise.text = releaseDate.toString()
            tvMovieRate.text = "Rate: ${voteAverage.toString()}"

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(movieResult, imageMovie)
            }
        }
    }
}