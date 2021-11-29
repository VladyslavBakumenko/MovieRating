package com.example.movierating.presentation.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.MovieRatingRepositoryImpl
import com.example.movierating.domain.GetMovieListUseCase
import com.example.movierating.domain.GetMoviesDataUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

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
    private val getMoviesDataUseCase = GetMoviesDataUseCase(repository)
    private val getMovieListUseCase = GetMovieListUseCase(repository)

    val movieList = getMovieListUseCase.getMovieList()
    val movieDataList = getMoviesDataUseCase.getMoviesDataUseCase()


    companion object {
        const val LINEAL_MODE = false
        const val TABEL_MODE = true
    }

}