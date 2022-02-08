package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movierating.R
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.databinding.FragmentTableBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainViewModel
import com.example.movierating.presentation.ui.recyclerViews.tableRv.MovieListTableAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TableFragment : Fragment() {

    private var binding: FragmentTableBinding? = null
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var movieListTableAdapter: MovieListTableAdapter

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

        binding?.changeFragmentToLinealButton?.setOnClickListener {
            launchRightFragment(LinealFragment.newInstance())
        }


        movieListTableAdapter.onMovieClickListener =
            object : MovieListTableAdapter.OnMovieClickListener {

                override fun onMovieClickListener(
                    movieResult: MovieResult
                ) {

                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, DetailsFragment.newInstance(movieResult))
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
        val itemsInOneColumns = 3
        val context = requireActivity().applicationContext
        movieListTableAdapter = MovieListTableAdapter()
        binding?.rvTableFilmList?.layoutManager = GridLayoutManager(context, itemsInOneColumns)

        binding?.rvTableFilmList?.adapter = movieListTableAdapter
        viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {

            val sortedMovieList = it.sortedBy { it.popularity }.reversed()
            movieListTableAdapter.movieDataList = sortedMovieList
        })
    }

    companion object {
        fun newInstance(): TableFragment = TableFragment()
    }

}