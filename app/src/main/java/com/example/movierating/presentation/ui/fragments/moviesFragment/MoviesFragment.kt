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
import com.example.movierating.presentation.ui.recyclerViews.tableRv.MovieListTableAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null
    private val viewModel: MoviesFragmentViewModel by viewModels()

    @Inject
    lateinit var movieListLinealAdapter: MovieListLinealAdapter

    @Inject
    lateinit var movieListTableAdapter: MovieListTableAdapter


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
        changeRecyclerView()
        viewModel.setValueToChangeRecyclerViewLiveData()

        binding?.changedFormat?.setOnClickListener {
            viewModel.changeRecyclerView()
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
    }

    private fun setUpLinealRecyclerView() {
        binding?.movieRecyclerView?.layoutManager = GridLayoutManager(context, 1)

        binding?.movieRecyclerView?.adapter = movieListLinealAdapter
        movieListLinealAdapter.movieDataList = viewModel.getLoaded()


        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                val sortedMovieList = it.sortedBy { it.popularity }.reversed()
                movieListLinealAdapter.movieDataList
                movieListLinealAdapter.movieDataList = sortedMovieList

            }
        })
        addPagination()
    }


    private fun setUpTableRecyclerView() {
        binding?.movieRecyclerView?.layoutManager = GridLayoutManager(context, 3)

        binding?.movieRecyclerView?.adapter = movieListTableAdapter
        movieListTableAdapter.movieDataList = viewModel.getLoaded()


        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                val sortedMovieList = it.sortedBy { it.popularity }.reversed()
                movieListTableAdapter.movieDataList
                movieListTableAdapter.movieDataList = sortedMovieList

            }
        })
        addPagination()
    }


    private fun addPagination() {
        val layoutManager = binding?.movieRecyclerView?.layoutManager as LinearLayoutManager
        binding?.movieRecyclerView?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems(page: Int) {
                viewModel.loadData(page)
            }
        })
    }

    private fun changeRecyclerView() {
        viewModel.changeRecyclerView.observe(viewLifecycleOwner, Observer {
            if (it == 0)
                setUpLinealRecyclerView()
            else
                setUpTableRecyclerView()
        })
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }

}