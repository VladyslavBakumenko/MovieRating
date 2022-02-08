package com.example.movierating.presentation.ui.fragments

import PaginationScrollListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierating.R
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.databinding.FragmentLinealBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainViewModel
import com.example.movierating.presentation.ui.recyclerViews.linealRv.MovieListLinealAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LinealFragment : Fragment() {

    private var binding: FragmentLinealBinding? = null
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var movieListLinealAdapter: MovieListLinealAdapter


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

        binding?.changeFragmentToTableButton?.setOnClickListener {
            launchRightFragment(TableFragment.newInstance())
        }

        movieListLinealAdapter.onMovieClickListener =
            object : MovieListLinealAdapter.OnMovieClickListener {
                override fun onMovieClick(
                    movieResult: MovieResult
                ) {

                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(
                            R.id.fragmentContainerView,
                            DetailsFragment.newInstance(movieResult)
                        ).commit()
                }
            }

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            Log.e("TAG", "onViewCreated: $it", )
        })

        viewModel.loadData(1)
    }

    private fun setUpRecyclerView() {
        movieListLinealAdapter = MovieListLinealAdapter()
        binding?.rvLinealMovieList?.adapter = movieListLinealAdapter

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                val sortedMovieList = it.sortedBy { it.popularity }.reversed()
                movieListLinealAdapter.movieDataList = sortedMovieList
            }
        })
        addPagination()
/*
        viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {
            val sortedMovieList = it.sortedBy { it.popularity }.reversed()
            movieListLinealAdapter.movieDataList = sortedMovieList
        })*/

    }

    private fun addPagination() {
        val layoutManager = binding?.rvLinealMovieList?.layoutManager as LinearLayoutManager
        binding?.rvLinealMovieList?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems(page: Int) {
                viewModel.loadData(page)
            }
        })
    }

    private fun launchRightFragment(tableFragment: TableFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, tableFragment)
            .commit()
    }

    companion object {
        fun newInstance(): LinealFragment = LinealFragment()
    }
}

