package com.example.movierating.presentation.ui.recyclerViews.tableRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.data.internet.MovieResult
import com.squareup.picasso.Picasso

class MovieListTableAdapter() :
    RecyclerView.Adapter<MovieItemTableViewHolder>() {


    var movieDataList: List<MovieResult> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemTableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.table_movie_item_true,
            parent,
            false
        )
        return MovieItemTableViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieItemTableViewHolder, position: Int) {
        with(viewHolder) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${movieDataList[position].posterPath}")
                .into(viewHolder.imageMovie)

            nameMovie.text = movieDataList[position].originalTitle
        }
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    interface OnMovieClickListener {
        fun movieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?,
        )
    }

}