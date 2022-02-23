package com.example.movierating.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movierating.data.internet.api.MovieApi
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.databinding.FragmentDetailsBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parseArgs()
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields()
    }


    private fun parseArgs(): MovieResult {
        return requireArguments()
            .getParcelable<MovieResult>(MainActivity.MOVIE_RESULT) as MovieResult
    }

    @SuppressLint("SetTextI18n")
    private fun setFields() {

        binding?.let {
            val args = parseArgs()

            Picasso.get().load("${MovieApi.IMAGE_TMDB_BEST_QUALITY}${args.posterPath}")
                .into(it.imageDetailsFragment)
            it.movieNameDetailsFragment.text = "Title: ${args.title}"
            it.movieAverageDetailsFragment.text = "Description: ${args.overview}"
            it.movieReleaseDetailsFragment.text = "Realise: ${args.releaseDate}"
            it.movieRateDetailsFragment.text = "Rate: ${args.voteAverage}"
            it.movieOriginalLanguageDetailsFragment.text =
                "Original language: ${args.originalLanguage}"
            it.moviePopularityDetailsFragment.text = "Popularity: ${args.popularity}"
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        val movie = requireArguments()
            .getParcelable<MovieResult>(MainActivity.MOVIE_RESULT)
        outState.putParcelable(CURRENT_MOVIE, movie)
        outState.putInt("2", 2)
        super.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val CURRENT_MOVIE = "current_movie"

        fun newInstance(movieResult: MovieResult): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(MainActivity.MOVIE_RESULT, movieResult)

            fragment.arguments = args
            return fragment
        }
    }
}