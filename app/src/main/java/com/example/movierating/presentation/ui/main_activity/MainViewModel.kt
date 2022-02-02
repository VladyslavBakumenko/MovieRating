package com.example.movierating.presentation.ui.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.data.repositorys_impl.MovieRatingRepository
import com.example.movierating.data.repositorys_impl.MovieRatingRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository: MovieRatingRepository = MovieRatingRepositoryImpl()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData() {
        coroutineScope.launch {
            movieRepository.loadData()
        }
    }

    fun getMoviesData(): LiveData<List<MovieResult>> {
        return movieRepository.getMoviesData()
    }

}
