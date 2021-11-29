package com.example.movierating.presentation.ui.test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.domain.MovieItem
import com.example.movierating.presentation.ui.main.MainViewModel
import com.example.movierating.presentation.ui.recycler_views.MovieListLinealAdapter
import com.example.movierating.presentation.ui.recycler_views.MovieListTableAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TestActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val PAGE = 1
        const val API_KEY = "a49cf8a5f42225880f049917a2262e81"
        const val LANGUAGE = "en-US"


        const val LINEAL_MODE = false
        const val TABEL_MODE = true
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var movieListLinealAdapter: MovieListLinealAdapter
    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var changeRecyclerViewButton: Button
    private lateinit var rvLineal: RecyclerView
    private lateinit var rvTable: RecyclerView


    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(R.layout.activity_test)





        viewModel.movieList.observe(this) {
        //    movieListLinealAdapter.movieList = it

        }

        var movieList = mutableListOf<MovieItem>()
        for(i in 0 until 100) {
            val item = MovieItem(i, "$i", "$i", "$i", i.toDouble(), " ")
            movieList.add(item)
        }

        val rvLinealMovieList = findViewById<RecyclerView>(R.id.rv_lineal_movie_list)
     //   movieListLinealAdapter = MovieListLinealAdapter()
     ////   movieListLinealAdapter.movieList = movieList

        rvLinealMovieList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        rvLinealMovieList.adapter = movieListLinealAdapter
    }


}
