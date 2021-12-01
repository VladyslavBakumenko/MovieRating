package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.presentation.ui.main_activity.MainViewModel
import com.example.movierating.presentation.ui.recycler_views.lineal_rv.MovieListLinealAdapter

class LinealFragment : Fragment() {

    private lateinit var rvLinealMovieList: RecyclerView
    private lateinit var movieListLinealAdapter: MovieListLinealAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var tabelFragment: TabelFragment
    private lateinit var changeFragmentToTable: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lineal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeFragmentToTable = view.findViewById(R.id.change_fragment_to_table_button)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpRecyclerView()

        tabelFragment = TabelFragment()
        changeFragmentToTable.setOnClickListener {
            launchRightFragment(tabelFragment)
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
        val formattedTotalMovieData = viewModel.movieDataList
        val linealMovieList = viewModel.linealMovieList

        rvLinealMovieList = requireActivity().findViewById(R.id.rv_lineal_movie_list)
        movieListLinealAdapter = MovieListLinealAdapter(formattedTotalMovieData)
        movieListLinealAdapter.linealMovieList = linealMovieList
        rvLinealMovieList.adapter = movieListLinealAdapter
    }

    private fun launchRightFragment(tabelFragment: TabelFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, tabelFragment)
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

