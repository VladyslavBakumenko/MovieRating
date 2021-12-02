package com.example.movierating.presentation.ui.recycler_views.table_rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.domain.FormattedTotalMovieData
import com.example.movierating.domain.MovieItem
import com.squareup.picasso.Picasso

class MovieListTableAdapter(formattedTotalMovieData: FormattedTotalMovieData) :
    RecyclerView.Adapter<MovieItemTableViewHolder>() {

    private val movieDataList = formattedTotalMovieData


    var tableMovieList = listOf<MovieItem>()
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
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position * 3]}")
                        .into(viewHolder.firstImageMovie)
                    firstNameMovie.text = movieDataList.formattedTotalTitles[position * 3]

                    Picasso
                        .get()
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position * 3 + 1]}")
                        .into(viewHolder.secondImageMovie)
                    secondNameMovie.text = movieDataList.formattedTotalTitles[position * 3 + 1]

                    Picasso
                        .get()
                        .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position * 3 + 2]}")
                        .into(viewHolder.thirdImageMovie)
                    thirdNameMovie.text = movieDataList.formattedTotalTitles[position * 3 + 2]
                }

            }
        }


        viewHolder.firstMovieCardView.setOnClickListener {
            onMovieClickListener?.onFirstMovieClick(
                movieDataList.formattedTotalTitles[position * 3],
                movieDataList.formattedTotalOverviews[position * 3],
                movieDataList.formattedTotalReleaseDates[position * 3].toString(),
                movieDataList.formattedTotalAverage[position * 3].toString(),
                movieDataList.formattedTotalOriginalLanguages[position * 3],
                movieDataList.formattedTotalPopularity[position * 3].toString(),
                "${movieDataList.formattedPosterPatches[position * 3]}"

            )
        }

        viewHolder.secondMovieCardView.setOnClickListener {
            onMovieClickListener?.onSecondMovieClick(
                movieDataList.formattedTotalTitles[position * 3 + 1],
                movieDataList.formattedTotalOverviews[position * 3 + 1],
                movieDataList.formattedTotalReleaseDates[position * 3 + 1].toString(),
                movieDataList.formattedTotalAverage[position * 3 + 1].toString(),
                movieDataList.formattedTotalOriginalLanguages[position * 3 + 1],
                movieDataList.formattedTotalPopularity[position * 3 + 1].toString(),
                "${movieDataList.formattedPosterPatches[position * 3 + 1]}"
            )
        }

        viewHolder.thirdMovieCardView.setOnClickListener {
            onMovieClickListener?.onThirdMovieClick(
                movieDataList.formattedTotalTitles[position * 3 + 2],
                movieDataList.formattedTotalOverviews[position * 3 + 2],
                movieDataList.formattedTotalReleaseDates[position * 3 + 2].toString(),
                movieDataList.formattedTotalAverage[position * 3 + 2].toString(),
                movieDataList.formattedTotalOriginalLanguages[position * 3 + 2],
                movieDataList.formattedTotalPopularity[position * 3 + 2].toString(),
                "${movieDataList.formattedPosterPatches[position * 3 + 2]}"
            )
        }

    }

    override fun getItemCount(): Int {
        return tableMovieList.size
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
            .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position]}")
            .into(viewHolder.firstImageMovie)
        viewHolder.firstNameMovie.text = movieDataList.formattedTotalTitles[position]

        Picasso
            .get()
            .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position + 1]}")
            .into(viewHolder.secondImageMovie)
        viewHolder.secondNameMovie.text = movieDataList.formattedTotalTitles[position + 1]

        Picasso
            .get()
            .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedPosterPatches[position + 2]}")
            .into(viewHolder.thirdImageMovie)
        viewHolder.thirdNameMovie.text = movieDataList.formattedTotalTitles[position + 2]
    }


}


