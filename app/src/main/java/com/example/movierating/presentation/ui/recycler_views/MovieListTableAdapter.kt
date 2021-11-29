package com.example.movierating.presentation.ui.recycler_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.domain.MovieItem

class MovieListTableAdapter: RecyclerView.Adapter<MovieListTableAdapter.MovieItemTableViewHolder>() {


    var movieList = listOf<MovieItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemTableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.table_movie_item,
            parent,
            false
        )
        return MovieItemTableViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieItemTableViewHolder, position: Int) {
        val movieItem = movieList[position]
/*        viewHolder.tvMovieDescription.text = movieItem.description
        viewHolder.tvMovieName.text = movieItem.name
        viewHolder.tvMovieRelise.text = movieItem.releaseDate
        viewHolder.tvMovieRate.text = movieItem.rate.toString()
        viewHolder.view.setOnClickListener {
            true
        }*/
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }

    class MovieItemTableViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val imageMovie = view.findViewById<ImageView>(R.id.image_movie_item_lineal)
        val tvMovieName = view.findViewById<TextView>(R.id.tv_movie_name_lineal)
        val tvMovieDescription = view.findViewById<TextView>(R.id.tv_movie_description_lineal)
        val tvMovieRate = view.findViewById<TextView>(R.id.tv_movie_rate_lineal)
        val tvMovieRelise = view.findViewById<TextView>(R.id.tv_movie_relise_lineal)
    }

}


