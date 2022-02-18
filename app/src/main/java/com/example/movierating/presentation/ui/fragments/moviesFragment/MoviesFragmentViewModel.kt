package com.example.movierating.presentation.ui.fragments.moviesFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierating.data.internet.erorHandling.safeApiCall
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.data.repositorys.movieRatingRepository.MovieRatingRepository
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

            safeApiCall({ movieRepository.loadData(page) },
                {
                    it?.let {
                        loadedMovies.addAll(it)
                        _movies.postValue(it)
                    }
                }, {
                    Log.d("dfdgfgfggdg", "went wrong")
                })
        }
    }


    fun getLoaded(): List<MovieResult> {
        return loadedMovies.toList()
    }


}