package com.example.movierating.presentation.ui.activitys.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.data.repositoriesImpl.MovieRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val movieRepository: MovieRatingRepository) : ViewModel() {

    //@Inject
    //lateinit var movieRepository: MovieRatingRepository
   // private val movieRepository: MovieRatingRepository = MovieRatingRepositoryImpl()


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
