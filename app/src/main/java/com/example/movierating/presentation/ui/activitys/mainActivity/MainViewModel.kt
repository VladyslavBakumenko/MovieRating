package com.example.movierating.presentation.ui.activitys.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _movies = MutableLiveData<List<MovieResult>?>()
    val movies: LiveData<List<MovieResult>?>
        get() = _movies


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData(page: Int) {
        coroutineScope.launch {
            val moviesData = movieRepository.loadData(page)
            _movies.postValue(moviesData)
        }
    }

    fun getMoviesData(): LiveData<List<MovieResult>> {
        return movieRepository.getMoviesData()
    }

}
