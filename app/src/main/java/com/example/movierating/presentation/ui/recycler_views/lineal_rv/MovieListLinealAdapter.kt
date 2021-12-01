package com.example.movierating.presentation.ui.recycler_views.lineal_rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.domain.FormattedTotalMovieData
import com.example.movierating.domain.MovieItem
import com.squareup.picasso.Picasso

class MovieListLinealAdapter(formattedTotalMovieData: FormattedTotalMovieData) :
    RecyclerView.Adapter<MovieItemLinealViewHolder>() {

    private val movieDataList = formattedTotalMovieData

    var linealMovieList = listOf<MovieItem>()
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieItemLinealViewHolder, position: Int) {
        with(holder) {
            Picasso
                .get()
                .load("${MovieApi.IMAGE_TMDB}${movieDataList.formattedTotalBackdropPaths[position]}")
                .into(holder.imageMovie)
            tvMovieDescription.text = movieDataList.formattedTotalOverviews[position]
            tvMovieName.text = movieDataList.formattedTotalTitles[position]
            tvMovieRelise.text = movieDataList.formattedTotalReleaseDates[position].toString()
            tvMovieRate.text = "Rate: ${movieDataList.formattedTotalAverage[position].toString()}"

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(
                    movieDataList.formattedTotalTitles[position],
                    movieDataList.formattedTotalOverviews[position],
                    movieDataList.formattedTotalReleaseDates[position].toString(),
                    movieDataList.formattedTotalAverage[position].toString(),
                    movieDataList.formattedTotalOriginalLanguages[position],
                    movieDataList.formattedTotalPopularity[position].toString(),
                    "${movieDataList.formattedPosterPatches[position]}"
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return linealMovieList.size
    }

    interface OnMovieClickListener {
        fun onMovieClick(
            title: String?,
            description: String?,
            realise: String?,
            rate: String?,
            originalLanguage: String?,
            popularity: String?,
            posterImage: String?
        )
    }
}



