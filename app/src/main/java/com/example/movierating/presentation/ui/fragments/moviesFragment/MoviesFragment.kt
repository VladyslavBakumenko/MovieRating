package com.example.movierating.presentation.ui.fragments.moviesFragment

import PaginationScrollListener
import android.content.res.Configuration
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierating.R
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.databinding.FragmentMoviesBinding
import com.example.movierating.databinding.LinealMovieItemBinding
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

    private var linearLayoutManager: LinearLayoutManager? = null
    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

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
        viewModel.currentRecyclerView.observe(viewLifecycleOwner, Observer {
        })

        if (savedInstanceState == null) viewModel.setDefaultValue()

        viewModel.networkError.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.loadData(1)


        binding?.changedFormat?.setOnClickListener {
            changeRecyclerView()
            viewModel.checkLastRecyclerView()
        }

        movieListLinealAdapter.onMovieClickListener =
            object : MovieListLinealAdapter.OnMovieClickListener {
                override fun onMovieClick(movieResult: MovieResult, imageView: ImageView) {

                    findNavController().navigate(

                        MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieResult),
                        FragmentNavigatorExtras(
                            imageView to "details_fragment_transition"
                        )
                    )
                }
            }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        if (checkOrientation() == Configuration.ORIENTATION_PORTRAIT &&
            viewModel.currentRecyclerView.value == 0 ||
            checkOrientation() ==
            Configuration.ORIENTATION_LANDSCAPE &&
            viewModel.currentRecyclerView.value == 0
        ) {
            linearLayoutManager = LinearLayoutManager(context)
            binding?.movieRecyclerView?.layoutManager = linearLayoutManager
            binding?.movieRecyclerView?.adapter = movieListLinealAdapter
        }

        if (checkOrientation() ==
            Configuration.ORIENTATION_PORTRAIT &&
            viewModel.currentRecyclerView.value == 1 ||
            checkOrientation() ==
            Configuration.ORIENTATION_LANDSCAPE &&
            viewModel.currentRecyclerView.value == 1
        ) {
            gridLayoutManager = if (checkOrientation() == Configuration.ORIENTATION_PORTRAIT)
                GridLayoutManager(context, 3)
            else
                GridLayoutManager(context, 5)

            binding?.movieRecyclerView?.layoutManager = gridLayoutManager
            binding?.movieRecyclerView?.adapter = movieListLinealAdapter
        }

        movieListLinealAdapter.setMovies(viewModel.getLoaded())

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                val sortedMovieList = it.sortedBy { it.popularity }.reversed()
                movieListLinealAdapter.addMovies(sortedMovieList)
            }
        })
        addPagination()
    }


    private fun addPagination() {
        binding?.movieRecyclerView?.addOnScrollListener(object : PaginationScrollListener() {
            override fun loadMoreItems(page: Int) {
                viewModel.loadData(page)
            }
        })
    }

    private fun changeRecyclerView() {
        if (movieListLinealAdapter.currentType == MovieListLinealAdapter.Type.LIST) {
            movieListLinealAdapter.toggleType()
            if (gridLayoutManager == null) {
                gridLayoutManager = when (resources.configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT ->
                        GridLayoutManager(context, 3)
                    Configuration.ORIENTATION_LANDSCAPE ->
                        GridLayoutManager(context, 5)
                    else -> GridLayoutManager(context, 3)
                }
            }
            binding?.movieRecyclerView?.layoutManager = gridLayoutManager
        } else {

            movieListLinealAdapter.toggleType()
            if (linearLayoutManager == null) {
                linearLayoutManager = LinearLayoutManager(context)
            }
            binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        }
    }

    private fun checkOrientation(): Int {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> Configuration.ORIENTATION_PORTRAIT
            Configuration.ORIENTATION_LANDSCAPE -> Configuration.ORIENTATION_LANDSCAPE

            else -> Configuration.ORIENTATION_PORTRAIT
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        linearLayoutManager = null
        gridLayoutManager = null
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }
}