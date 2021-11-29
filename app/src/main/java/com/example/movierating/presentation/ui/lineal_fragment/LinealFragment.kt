package com.example.movierating.presentation.ui.lineal_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.domain.MovieItem
import com.example.movierating.presentation.ui.details_fragment.DetailsFragment
import com.example.movierating.presentation.ui.main.MainViewModel
import com.example.movierating.presentation.ui.recycler_views.MovieListLinealAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class LinealFragment : Fragment() {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var rvLinealMovieList: RecyclerView
    private lateinit var movieListLinealAdapter: MovieListLinealAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var detailsFragment: DetailsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lineal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val formattedTotalMovieData = viewModel.movieDataList

        rvLinealMovieList = view.findViewById(R.id.rv_lineal_movie_list)
        movieListLinealAdapter = MovieListLinealAdapter(formattedTotalMovieData)

        val movieList = mutableListOf<MovieItem>()
        for (i in 0 until 600) {
            val item = MovieItem(null, null, null, null, null, null)
            movieList.add(item)
        }

        movieListLinealAdapter.movieList = movieList
        rvLinealMovieList.adapter = movieListLinealAdapter


        movieListLinealAdapter.onMovieClickListener = object : MovieListLinealAdapter.OnMovieClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable = CompositeDisposable()
    }



    private fun setUpRecyclerView() {
        val movieDataList = mutableListOf<MovieItem>()

        for (i in 0 until 100) {
            val item = MovieItem(i, "$i", "$i", "$i", i.toDouble(), " ")
            movieDataList.add(item)
        }
   //     movieListLinealAdapter = MovieListLinealAdapter()
   //     movieListLinealAdapter.movieList = movieDataList
        rvLinealMovieList.adapter = movieListLinealAdapter
    }


    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val REALISE = "realise"
        const val RATE = "rate"
        const val ORIGINAL_LANGUAGE = "originalLanguage"
        const val POPULARITY = "popularity"
        const val IMAGE = "image"
    }
}

