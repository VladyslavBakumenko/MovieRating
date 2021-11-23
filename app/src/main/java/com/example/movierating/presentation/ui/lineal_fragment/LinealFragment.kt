package com.example.movierating.presentation.ui.lineal_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.domain.MovieItem
import com.example.movierating.presentation.ui.recycler_views.MovieListLinealAdapter
import com.example.movierating.presentation.ui.recycler_views.MovieListTableAdapter
import com.example.movierating.presentation.ui.table_fragment.TabelFragment

class LinealFragment : Fragment() {

    private lateinit var homeViewModel: LinealFragmentViewModel

    private lateinit var rvLinealMovieList: RecyclerView
    private lateinit var movieListLinealAdapter: MovieListLinealAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lineal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListLinealAdapter = MovieListLinealAdapter()
        rvLinealMovieList = view.findViewById(R.id.rv_lineal_movie_list)

        val movieList = mutableListOf<MovieItem>()
        for(i in 0 until 100) {
            val item = MovieItem(i, "$i", "$i", "$i", i.toDouble(), " ")
            movieList.add(item)
        }

        movieListLinealAdapter.movieList = movieList

        rvLinealMovieList.adapter = movieListLinealAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}