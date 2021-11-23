
package com.example.movierating.presentation.ui.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.domain.MovieItem

class MovieListLinealAdapter: RecyclerView.Adapter<MovieItemLinealViewHolder>() {


    var movieList = listOf<MovieItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemLinealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.lineal_movie_item,
            parent,
            false
        )
        return MovieItemLinealViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieItemLinealViewHolder, position: Int) {
        val movieItem = movieList[position]
        viewHolder.tvMovieDescription.text = movieItem.description
        viewHolder.tvMovieName.text = movieItem.name
        viewHolder.tvMovieRelise.text = movieItem.releaseDate
        viewHolder.tvMovieRate.text = movieItem.rate.toString()
        viewHolder.view.setOnClickListener {
            true
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}



