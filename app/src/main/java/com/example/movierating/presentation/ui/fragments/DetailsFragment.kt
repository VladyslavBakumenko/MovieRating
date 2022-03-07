package com.example.movierating.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.movierating.data.internet.api.MovieApi
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.databinding.FragmentDetailsBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields()
    }




    @SuppressLint("SetTextI18n")
    private fun setFields() {

        binding?.let {

            Picasso.get().load("${MovieApi.IMAGE_TMDB_BEST_QUALITY}${args.movie.posterPath}")
                .into(it.imageDetailsFragment)
            it.movieNameDetailsFragment.text = "Title: ${args.movie.title}"
            it.movieAverageDetailsFragment.text = "Description: ${args.movie.overview}"
            it.movieReleaseDetailsFragment.text = "Realise: ${args.movie.releaseDate}"
            it.movieRateDetailsFragment.text = "Rate: ${args.movie.voteAverage}"
            it.movieOriginalLanguageDetailsFragment.text =
                "Original language: ${args.movie.originalLanguage}"
            it.moviePopularityDetailsFragment.text = "Popularity: ${args.movie.popularity}"
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