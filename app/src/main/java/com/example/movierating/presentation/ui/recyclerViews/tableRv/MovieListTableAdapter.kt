package com.example.movierating.presentation.ui.recyclerViews.tableRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieResult

class MovieListTableAdapter :
    RecyclerView.Adapter<MovieItemTableViewHolder>() {


    var movieDataList: List<MovieResult> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemTableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.table_movie_item,
            parent,
            false
        )
        return MovieItemTableViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieItemTableViewHolder, position: Int) {
        viewHolder.bind(movieDataList[position], onMovieClickListener)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    interface OnMovieClickListener {
        fun onMovieClickListener(movieResult: MovieResult)
    }

}