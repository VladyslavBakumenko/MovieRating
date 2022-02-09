package com.example.movierating.presentation.ui.fragments.moviesFragment

import PaginationScrollListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierating.R
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.databinding.FragmentMoviesBinding
import com.example.movierating.presentation.ui.fragments.DetailsFragment
import com.example.movierating.presentation.ui.recyclerViews.linealRv.MovieListLinealAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null
    private val viewModel: MoviesFragmentViewModel by viewModels()

    @Inject
    lateinit var movieListLinealAdapter: MovieListLinealAdapter

    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private val gridLayoutManager by lazy { GridLayoutManager(context, 3) }

//    @Inject
//    lateinit var movieListTableAdapter: MovieListTableAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData(1)

        binding?.changedFormat?.setOnClickListener {
            changeRecyclerView()
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

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        binding?.movieRecyclerView?.adapter = movieListLinealAdapter

        movieListLinealAdapter.setMovies(viewModel.getLoaded())

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                val sortedMovieList = it.sortedBy { it.popularity }.reversed()
//                movieListLinealAdapter.movieDataList
                movieListLinealAdapter.addMovies(sortedMovieList)

            }
        })
        addPagination()
    }

    private fun addPagination() {
        val layoutManager = binding?.movieRecyclerView?.layoutManager
        binding?.movieRecyclerView?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems(page: Int) {
                viewModel.loadData(page)
            }
        })
    }

    private fun changeRecyclerView() {
        if (movieListLinealAdapter.currentType == MovieListLinealAdapter.Type.LIST) {
            movieListLinealAdapter.toggleType()
            binding?.movieRecyclerView?.layoutManager = gridLayoutManager
        } else {
            movieListLinealAdapter.toggleType()
            binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        }
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }

}