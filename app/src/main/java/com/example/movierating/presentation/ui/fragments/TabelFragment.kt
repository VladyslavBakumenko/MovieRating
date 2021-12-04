package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.presentation.ui.main_activity.MainViewModel
import com.example.movierating.presentation.ui.recycler_views.table_rv.MovieListTableAdapter

class TabelFragment : Fragment() {

    private lateinit var rvTableMovieList: RecyclerView
    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var viewModel: MainViewModel
    private lateinit var changeFragmentToLineal: Button
    private lateinit var linealFragment: LinealFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeFragmentToLineal = view.findViewById(R.id.change_fragment_to_lineal_button)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpRecyclerView()

        detailsFragment = DetailsFragment()

        linealFragment = LinealFragment()
        changeFragmentToLineal.setOnClickListener {
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

        rvTableMovieList = requireActivity().findViewById(R.id.rv_table_film_list)
        movieListTableAdapter = MovieListTableAdapter()
        rvTableMovieList.adapter = movieListTableAdapter
        viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {

            val sortedMovieList = it.sortedBy { it.popularity }.reversed()

            movieListTableAdapter.movieDataList = sortedMovieList
        })
    }
}