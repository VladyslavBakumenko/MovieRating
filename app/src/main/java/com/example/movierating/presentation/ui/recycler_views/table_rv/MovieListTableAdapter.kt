package com.example.movierating.presentation.ui.recycler_views.table_rv

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
            R.layout.table_movie_item,
            parent,
            false
        )
        return MovieItemTableViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieItemTableViewHolder, position: Int) {
        when (position) {
            0 -> {
                setFirstMovieItem(position, viewHolder)
            }
            else -> {
                with(viewHolder) {
                    Picasso
                        .get()
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList[position * 3].posterPath}")
                        .into(viewHolder.firstImageMovie)
                    firstNameMovie.text = movieDataList[position * 3].title

                    Picasso
                        .get()
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList[position * 3 + 1].posterPath}")
                        .into(viewHolder.secondImageMovie)
                    secondNameMovie.text = movieDataList[position * 3 + 1].title

                    Picasso
                        .get()
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList[position * 3 + 2].posterPath}")
                        .into(viewHolder.thirdImageMovie)
                    thirdNameMovie.text = movieDataList[position * 3 + 2].title
                }

            }
        }


        viewHolder.firstMovieCardView.setOnClickListener {
            onMovieClickListener?.onFirstMovieClick(
                movieDataList[position * 3].title,
                movieDataList[position * 3].overview,
                movieDataList[position * 3].releaseDate.toString(),
                movieDataList[position * 3].voteAverage.toString(),
                movieDataList[position * 3].originalLanguage,
                movieDataList[position * 3].popularity.toString(),
                "${movieDataList[position * 3].posterPath}"

            )
        }

        viewHolder.secondMovieCardView.setOnClickListener {
            onMovieClickListener?.onSecondMovieClick(
                movieDataList[position * 3 + 1].title,
                movieDataList[position * 3 + 1].overview,
                movieDataList[position * 3 + 1].releaseDate.toString(),
                movieDataList[position * 3 + 1].voteAverage.toString(),
                movieDataList[position * 3 + 1].originalLanguage,
                movieDataList[position * 3 + 1].popularity.toString(),
                "${movieDataList[position * 3 + 1].posterPath}"
            )
        }

        viewHolder.thirdMovieCardView.setOnClickListener {
            onMovieClickListener?.onThirdMovieClick(
                movieDataList[position * 3 + 2].title,
                movieDataList[position * 3 + 2].overview,
                movieDataList[position * 3 + 2].releaseDate.toString(),
                movieDataList[position * 3 + 2].voteAverage.toString(),
                movieDataList[position * 3 + 2].originalLanguage,
                movieDataList[position * 3 + 2].popularity.toString(),
                "${movieDataList[position * 3 + 2].posterPath}"
            )
        }

    }

    override fun getItemCount(): Int {
        return movieDataList.size / MOVIES_IN_ONE_ITEM
    }


    interface OnMovieClickListener {
        fun onFirstMovieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?,

            )

        fun onSecondMovieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?
        )

        fun onThirdMovieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?
        )
    }

    private fun setFirstMovieItem(position: Int, viewHolder: MovieItemTableViewHolder) {
        Picasso
            .get()
            .load("${MovieApi.IMAGE_TMDB}${movieDataList[position].posterPath}")
            .into(viewHolder.firstImageMovie)
        viewHolder.firstNameMovie.text = movieDataList[position].title

        Picasso
            .get()
            .load("${MovieApi.IMAGE_TMDB}${movieDataList[position + 1].posterPath}")
            .into(viewHolder.secondImageMovie)
        viewHolder.secondNameMovie.text = movieDataList[position + 1].title

        Picasso
            .get()
            .load("${MovieApi.IMAGE_TMDB}${movieDataList[position + 2].posterPath}")
            .into(viewHolder.thirdImageMovie)
        viewHolder.thirdNameMovie.text = movieDataList[position + 2].title
    }

    companion object {
        const val MOVIES_IN_ONE_ITEM = 3
    }
}


