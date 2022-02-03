package com.example.movierating.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var realise: String
    private lateinit var rate: String
    private lateinit var originalLanguage: String
    private lateinit var popularity: String
    private lateinit var image: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initArgs()
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields()
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
        binding?.let {
            Picasso.get().load("${MovieApi.IMAGE_TMDB_BEST_QUALITY}$image")
                .into(it.imageDetailsFragment)
            it.movieNameDetailsFragment.text = "Title: $title"
            it.movieAverageDetailsFragment.text = "Description: $description"
            it.movieReleaseDetailsFragment.text = "Realise: $realise"
            it.movieRateDetailsFragment.text = "Rate: $rate"
            it.movieOriginalLanguageDetailsFragment.text = "Original language: $originalLanguage"
            it.moviePopularityDetailsFragment.text = "Popularity: $popularity"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}