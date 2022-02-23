package com.example.movierating.presentation.ui.fragments.moviesFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.erorHandling.safeApiCall
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.data.repositorys.movieRatingRepository.MovieRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor
    (private val movieRepository: MovieRatingRepository) : ViewModel() {

    private val loadedMovies = mutableListOf<MovieResult>()

    private val _movies = MutableLiveData<List<MovieResult>?>()
    val movies: LiveData<List<MovieResult>?>
        get() = _movies

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    private val _currentRecyclerView = MutableLiveData<Int>()
    val currentRecyclerView: LiveData<Int>
        get() = _currentRecyclerView


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData(page: Int) {

        safeApiCall({ movieRepository.loadData(page) },
            {
                it?.let {
                    loadedMovies.addAll(it)
                    _movies.postValue(it)
                }
            }, {
                _networkError.value = true
            })
    }


    fun checkLastRecyclerView() {
        if (_currentRecyclerView.value == 0) _currentRecyclerView.value = 1
        else _currentRecyclerView.value = 0
    }

    fun setDefaultValue() {
        _currentRecyclerView.value = 0
    }


    fun getLoaded(): List<MovieResult> {
        return loadedMovies.toList()
    }

}