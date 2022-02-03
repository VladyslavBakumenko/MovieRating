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
import com.example.movierating.databinding.FragmentLinealBinding
import com.example.movierating.presentation.ui.mainActivity.MainViewModel
import com.example.movierating.presentation.ui.recyclerViews.linealRv.MovieListLinealAdapter

class LinealFragment : Fragment() {

    private var binding: FragmentLinealBinding? = null

    private lateinit var movieListLinealAdapter: MovieListLinealAdapter
    private val viewModel: MainViewModel by viewModels()
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var tableFragment: TableFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinealBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        tableFragment = TableFragment()
        binding?.changeFragmentToTableButton?.setOnClickListener {
            launchRightFragment(tableFragment)
        }

        movieListLinealAdapter.onMovieClickListener =
            object : MovieListLinealAdapter.OnMovieClickListener {
                override fun onMovieClick(
                    title: String?,
                    description: String?,
                    realise: String?,
                    rate: String?,
                    originalLanguage: String?,
                    popularity: String?,
                    posterImage: String?
                ) {
                    val args = Bundle()
                    args.putString(TITLE, title)
                    args.putString(DESCRIPTION, description)
                    args.putString(REALISE, realise)
                    args.putString(RATE, rate)
                    args.putString(ORIGINAL_LANGUAGE, originalLanguage)
                    args.putString(POPULARITY, popularity)
                    args.putString(IMAGE, posterImage)

                    detailsFragment = DetailsFragment()
                    detailsFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, detailsFragment)
                        .commit()
                }
            }
    }

    private fun setUpRecyclerView() {
        movieListLinealAdapter = MovieListLinealAdapter()
        binding?.rvLinealMovieList?.adapter = movieListLinealAdapter

        viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {
            val sortedMovieList = it.sortedBy { it.popularity }.reversed()
            movieListLinealAdapter.movieDataList = sortedMovieList
        })
    }

    private fun launchRightFragment(tableFragment: TableFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, tableFragment)
            .commit()
    }

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val REALISE = "realise"
        const val RATE = "rate"
        const val ORIGINAL_LANGUAGE = "originalLanguage"
        const val POPULARITY = "popularity"
        const val IMAGE = "image"
    }
}

