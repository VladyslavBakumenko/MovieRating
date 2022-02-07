package com.example.movierating.presentation.ui.recyclerViews.tableRv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.databinding.TableMovieItemBinding
import com.squareup.picasso.Picasso

class MovieItemTableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TableMovieItemBinding.bind(view)

    private val imageMovie = binding.filmImage
    private val nameMovie = binding.filmName


    fun bind(
        movieResult: MovieResult,
        onMovieClickListener: MovieListTableAdapter.OnMovieClickListener?
    ) {
        with(movieResult) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${posterPath}")
                .into(imageMovie)

            nameMovie.text = movieResult.originalTitle

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClickListener(movieResult)
            }
        }
    }
}