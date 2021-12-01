package com.example.movierating.presentation.ui.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movierating.data.repositorys_impl.MovieRatingRepositoryImpl
import com.example.movierating.domain.use_cases.GetMovieListLinealUseCase
import com.example.movierating.domain.use_cases.GetMovieTableListUseCase
import com.example.movierating.domain.use_cases.GetMoviesDataUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = MovieRatingRepositoryImpl
    private val getMoviesDataUseCase = GetMoviesDataUseCase(repository)
    private val getMovieLinealListUseCase = GetMovieListLinealUseCase(repository)
    private val getMovieTableListUseCase = GetMovieTableListUseCase(repository)

    val linealMovieList = getMovieLinealListUseCase.getMovieLinealList()
    val tableMovieList = getMovieTableListUseCase.getMovieTableList()
    val movieDataList = getMoviesDataUseCase.getMoviesDataUseCase()
}