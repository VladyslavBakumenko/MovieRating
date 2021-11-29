package com.example.movierating.presentation.ui.table_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.domain.MovieItem
import com.example.movierating.presentation.ui.recycler_views.MovieListLinealAdapter
import com.example.movierating.presentation.ui.recycler_views.MovieListTableAdapter
import io.reactivex.disposables.CompositeDisposable

class TabelFragment : Fragment() {

    private lateinit var homeViewModel: TabelFragmentViewModel

    private lateinit var rvTableMovieList: RecyclerView
    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var compositeDisposable : CompositeDisposable



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvTableMovieList = view.findViewById(R.id.rv_table_film_list)
        movieListTableAdapter = MovieListTableAdapter()

        val movieList = mutableListOf<MovieItem>()
        for(i in 0 until 100) {
            val item = MovieItem(i, "$i", "$i", "$i", i.toDouble(), " ")
            movieList.add(item)
        }

        movieListTableAdapter.movieList = movieList
        rvTableMovieList.adapter = movieListTableAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}