package com.example.movierating.presentation.ui.fragments.moviesFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.data.movieRatingRepository.MovieRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor
    (private val movieRepository: MovieRatingRepository) : ViewModel() {

    private val loadedMovies = mutableListOf<MovieResult>()

    private val _movies = MutableLiveData<List<MovieResult>?>()
    val movies: LiveData<List<MovieResult>?>
        get() = _movies


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData(page: Int) {
        coroutineScope.launch {
            val moviesData = movieRepository.loadData(page)

            moviesData?.let {
                loadedMovies.addAll(moviesData)
            }
            _movies.postValue(moviesData)
        }
    }


    fun getLoaded(): List<MovieResult> {
        return loadedMovies.toList()
    }


}