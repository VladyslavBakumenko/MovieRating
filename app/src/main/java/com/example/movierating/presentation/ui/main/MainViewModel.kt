package com.example.movierating.presentation.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.MovieRatingRepositoryImpl
import com.example.movierating.domain.GetMovieListUseCase
import com.example.movierating.domain.MovieItem

class MainViewModel(application: Application): AndroidViewModel(application) {

    fun createLinealMovieList(): List<MovieItem> {
        val linealMovieList = mutableListOf<MovieItem>()

        for(i in 0 until TOTAL_MOVIE_ITEM) {
            val item = MovieItem(i," ", " ", " ", 1.0, " ")
            linealMovieList.add(item)
        }
        return linealMovieList
    }

    fun createTableMovieList(): List<MovieItem>{
        val tableMovieItem = mutableListOf<MovieItem>()
        for(i in 0 until TOTAL_MOVIE_ITEM) {
            val item = MovieItem(1,"1", "1", "1", 1.0, " ")
            tableMovieItem.add(item)
        }
        return tableMovieItem

    }

    private val _fragmentStatus = MutableLiveData<Boolean>()
    val fragmentStatus: LiveData<Boolean>
        get() = _fragmentStatus


    fun changeModeToLineal() {
        _fragmentStatus.value = LINEAL_MODE
    }
    fun changeModeToTabel() {
        _fragmentStatus.value = TABEL_MODE
    }

    private val repository = MovieRatingRepositoryImpl

    private val getMovieListUseCase = GetMovieListUseCase(repository)

    val movieList = getMovieListUseCase.getMovieList()





    companion object {
        const val LINEAL_MODE = false
        const val TABEL_MODE = true
        const val TOTAL_MOVIE_ITEM = 10000
        const val TOTAL_PAGES = 500
        const val MOVIE_ITEMS_IN_ONE_TABLE_ITEM = 3
    }

}