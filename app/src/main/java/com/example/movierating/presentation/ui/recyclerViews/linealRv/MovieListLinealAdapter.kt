package com.example.movierating.presentation.ui.recyclerViews.linealRv


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieResult

class MovieListLinealAdapter() :
    RecyclerView.Adapter<MovieItemLinealViewHolder>() {


    var movieDataList: List<MovieResult> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemLinealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.lineal_movie_item,
            parent,
            false
        )
        return MovieItemLinealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemLinealViewHolder, position: Int) {
        holder.bind(movieDataList[position], onMovieClickListener)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    interface OnMovieClickListener {
        fun onMovieClick(movieResult: MovieResult)
    }
}



