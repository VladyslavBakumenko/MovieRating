package com.example.movierating.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.presentation.ui.recycler_views.MovieListLinealAdapter
import com.example.movierating.presentation.ui.table_fragment.TabelFragment
import com.example.movierating.presentation.ui.lineal_fragment.LinealFragment
import com.example.movierating.presentation.ui.recycler_views.MovieListTableAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var movieListLinealAdapter: MovieListLinealAdapter
    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var changeFragmentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        changeFragmentButton.setOnClickListener {
            launchRightFragment()
        }
    }

    fun initViews() {
        changeFragmentButton = findViewById(R.id.change_fragment_button)
        movieListLinealAdapter = MovieListLinealAdapter()
        movieListTableAdapter = MovieListTableAdapter()
    }


    private fun setUpLinealRecyclerView() {
        val linealFragment = LinealFragment()

        val rvLinealMovieList = findViewById<RecyclerView>(R.id.rv_lineal_movie_list)
        movieListLinealAdapter = MovieListLinealAdapter()
        rvLinealMovieList.layoutManager = LinearLayoutManager(
            linealFragment.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.movieList.observe(this) {
            movieListLinealAdapter.movieList = it
        }
        rvLinealMovieList.adapter = movieListLinealAdapter

    }

    private fun setUpTableRecyclerView() {
        val tableFragment = TabelFragment()

        val rvTableMovieList = findViewById<RecyclerView>(R.id.rv_table_film_list)
        movieListTableAdapter = MovieListTableAdapter()
        rvTableMovieList.layoutManager = LinearLayoutManager(
            tableFragment.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.movieList.observe(this) {
            movieListTableAdapter.movieList = it
        }
        rvTableMovieList.adapter = movieListTableAdapter
    }


    private fun launchRightFragment() {
        if (viewModel.fragmentStatus.value == null) {
            viewModel.changeModeToLineal()
        }

        when (viewModel.fragmentStatus.value) {

            LINEAL_MODE -> {
                val linealFragment = LinealFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, linealFragment)
                    .commit()
                viewModel.changeModeToTabel()
   //             setUpLinealRecyclerView()
                Log.d("TESTMODECHANGER", "лінія")

            }
            TABEL_MODE -> {
                val tableFragment = TabelFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, tableFragment)
                    .commit()
                viewModel.changeModeToLineal()
    //            setUpTableRecyclerView()
                Log.d("TESTMODECHANGER", "таблиця")
            }
            else -> throw RuntimeException("Unknown fragment mode")
        }
    }

    companion object {
        const val LINEAL_MODE = false
        const val TABEL_MODE = true
    }
}
