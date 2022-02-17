package com.example.movierating.presentation.ui.recyclerViews.linealRv


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.presentation.ui.recyclerViews.tableRv.MovieItemTableViewHolder

class MovieListLinealAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    var currentType = Type.LIST

    private val movieDataList = mutableListOf<MovieResult>()

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.LIST.value -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.lineal_movie_item,
                    parent,
                    false
                )
                MovieItemLinealViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.table_movie_item,
                    parent,
                    false
                )
                MovieItemTableViewHolder(view)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (currentType) {
            Type.LIST -> Type.LIST.value
            Type.GRID -> Type.GRID.value
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemLinealViewHolder) {
            holder.bind(movieDataList[position], onMovieClickListener)
        } else if (holder is MovieItemTableViewHolder) {
            holder.bind(movieDataList[position], onMovieClickListener)
        }
    }

    fun toggleType() {
        currentType = if (currentType == Type.LIST) Type.GRID else Type.LIST
//        notifyDataSetChanged()
    }

    fun setMovies(movies: List<MovieResult>) {
        movieDataList.clear()
        movieDataList.addAll(movies)
        notifyItemRangeInserted(0, movies.size)
    }

    fun addMovies(movies: List<MovieResult>) {
        movieDataList.addAll(movies)
        notifyItemRangeInserted(itemCount - 1, movies.size)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    enum class Type(val value: Int) {
        LIST(0), GRID(1)
    }

    interface OnMovieClickListener {
        fun onMovieClick(movieResult: MovieResult)
    }
}



