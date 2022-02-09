package com.example.movierating.presentation.ui.fragments.moviesFragment

import android.util.Log
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
import kotlin.math.log

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor
    (private val movieRepository: MovieRatingRepository) : ViewModel() {

    private val loadedMovies = mutableListOf<MovieResult>()

    private val _movies = MutableLiveData<List<MovieResult>?>()
    val movies: LiveData<List<MovieResult>?>
        get() = _movies

//    private val _changeRecyclerView = MutableLiveData<Int>()
//    val changeRecyclerView: LiveData<Int>
//        get() = _changeRecyclerView

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData(page: Int) {
        coroutineScope.launch {
            val moviesData = movieRepository.loadData(page)

            moviesData?.let {
                loadedMovies.addAll(moviesData)
            }

//            for (i in 0 until MOVIES_IN_ONE_PAGE) {
//                moviesData?.get(i)?.let {
//                    loadedMovies.add(it)
//                }
//            }


            Log.d("dfgfdghbghf", "loadData: ")
            Log.d("dfgfdghbghf", "${loadedMovies.size}")
            _movies.postValue(moviesData)
        }
    }

//    fun changeRecyclerView() {
//        if (_changeRecyclerView.value == 0)
//            _changeRecyclerView.postValue(1)
//        else
//            _changeRecyclerView.postValue(0)
//    }
//
//    fun setValueToChangeRecyclerViewLiveData() {
//        _changeRecyclerView.postValue(0)
//    }


    fun getLoaded(): List<MovieResult> {
        return loadedMovies.toList()
    }

    companion object {
        const val MOVIES_IN_ONE_PAGE = 20
    }


}