package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.databinding.FragmentTableBinding
import com.example.movierating.presentation.ui.mainActivity.MainViewModel
import com.example.movierating.presentation.ui.recyclerViews.tableRv.MovieListTableAdapter

class TableFragment : Fragment() {

    private var binding: FragmentTableBinding? = null

    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var detailsFragment: DetailsFragment
    private val viewModel: MainViewModel by viewModels()
    private lateinit var linealFragment: LinealFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        detailsFragment = DetailsFragment()

        linealFragment = LinealFragment()
        binding?.changeFragmentToLinealButton?.setOnClickListener {
            launchRightFragment(linealFragment)
        }


        movieListTableAdapter.onMovieClickListener =
            object : MovieListTableAdapter.OnMovieClickListener {
                override fun onFirstMovieClick(
                    title: String?,
                    description: String?,
                    realise: String?,
                    rate: String?,
                    originalLanguage: String?,
                    popularity: String?,
                    posterImage: String?
                ) {
                    val args = Bundle()
                    args.putString(LinealFragment.TITLE, title)
                    args.putString(LinealFragment.DESCRIPTION, description)
                    args.putString(LinealFragment.REALISE, realise)
                    args.putString(LinealFragment.RATE, rate)
                    args.putString(LinealFragment.ORIGINAL_LANGUAGE, originalLanguage)
                    args.putString(LinealFragment.POPULARITY, popularity)
                    args.putString(LinealFragment.IMAGE, posterImage)

                    detailsFragment = DetailsFragment()
                    detailsFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, detailsFragment)
                        .commit()
                }

                override fun onSecondMovieClick(
                    title: String?,
                    description: String?,
                    realise: String?,
                    rate: String?,
                    originalLanguage: String?,
                    popularity: String?,
                    posterImage: String?
                ) {
                    val args = Bundle()
                    args.putString(LinealFragment.TITLE, title)
                    args.putString(LinealFragment.DESCRIPTION, description)
                    args.putString(LinealFragment.REALISE, realise)
                    args.putString(LinealFragment.RATE, rate)
                    args.putString(LinealFragment.ORIGINAL_LANGUAGE, originalLanguage)
                    args.putString(LinealFragment.POPULARITY, popularity)
                    args.putString(LinealFragment.IMAGE, posterImage)

                    detailsFragment = DetailsFragment()
                    detailsFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, detailsFragment)
                        .commit()
                }

                override fun onThirdMovieClick(
                    title: String?,
                    description: String?,
                    realise: String?,
                    rate: String?,
                    originalLanguage: String?,
                    popularity: String?,
                    posterImage: String?
                ) {
                    val args = Bundle()
                    args.putString(LinealFragment.TITLE, title)
                    args.putString(LinealFragment.DESCRIPTION, description)
                    args.putString(LinealFragment.REALISE, realise)
                    args.putString(LinealFragment.RATE, rate)
                    args.putString(LinealFragment.ORIGINAL_LANGUAGE, originalLanguage)
                    args.putString(LinealFragment.POPULARITY, popularity)
                    args.putString(LinealFragment.IMAGE, posterImage)

                    detailsFragment = DetailsFragment()
                    detailsFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, detailsFragment)
                        .commit()
                }
            }
    }

    private fun launchRightFragment(linealFragment: LinealFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, linealFragment)
            .commit()
    }

    private fun setUpRecyclerView() {
        movieListTableAdapter = MovieListTableAdapter()
        binding?.rvTableFilmList?.adapter = movieListTableAdapter
        viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {

            val sortedMovieList = it.sortedBy { it.popularity }.reversed()

            movieListTableAdapter.movieDataList = sortedMovieList
        })
    }
}