package com.example.movierating.data.repositoriesImpl.movieRatingRepository

import androidx.lifecycle.LiveData
import com.example.movierating.data.internet.movies.MovieResult

interface MovieRatingRepository {
    suspend fun loadData(page: Int) : List<MovieResult>?
    fun addMovies(movies: List<MovieResult>)
    fun getMoviesData(): LiveData<List<MovieResult>>
    fun deleteOllMovies()
}