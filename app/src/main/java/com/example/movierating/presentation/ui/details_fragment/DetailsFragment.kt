package com.example.movierating.presentation.ui.details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movierating.R
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.presentation.ui.lineal_fragment.LinealFragment
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var realise: String
    private lateinit var rate: String
    private lateinit var originalLanguage: String
    private lateinit var popularity: String
    private lateinit var image: String

    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieRealise: TextView
    private lateinit var movieRate: TextView
    private lateinit var movieOriginalLanguage: TextView
    private lateinit var moviePopularity: TextView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initArgs()
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setFields()
    }

    private fun initViews(view: View) {
        movieImage = view.findViewById(R.id.image_details_fragment)
        movieTitle = view.findViewById(R.id.movie_name_details_fragment)
        movieDescription = view.findViewById(R.id.movie_average_details_fragment)
        movieRealise = view.findViewById(R.id.movie_release_details_fragment)
        movieRate = view.findViewById(R.id.movie_rate_details_fragment)
        movieOriginalLanguage = view.findViewById(R.id.movie_original_language_details_fragment)
        moviePopularity = view.findViewById(R.id.movie_popularity_details_fragment)
    }

    private fun initArgs() {
        arguments?.let {
            title = it.getString(LinealFragment.TITLE).toString()
            description = it.getString(LinealFragment.DESCRIPTION).toString()
            realise = it.getString(LinealFragment.REALISE).toString()
            rate = it.getString(LinealFragment.RATE).toString()
            originalLanguage = it.getString(LinealFragment.ORIGINAL_LANGUAGE).toString()
            popularity = it.getString(LinealFragment.POPULARITY).toString()
            image = it.getString(LinealFragment.IMAGE).toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setFields() {
        Log.d("fdgfgfd", image)
        Log.d("fdgfgfd", "${MovieApi.IMAGE_TMDB_POSTER}$image")
        Picasso.get().load("${MovieApi.IMAGE_TMDB_POSTER}$image").into(movieImage)
        movieTitle.text = "Title: $title"
        movieDescription.text = "Description: $description"
        movieRealise.text = "Realise: $realise"
        movieRate.text = "Rate: $rate"
        movieOriginalLanguage.text = "Original language: $originalLanguage"
        moviePopularity.text = "Popularity: $popularity"
    }

}









