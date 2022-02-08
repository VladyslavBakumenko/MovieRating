package com.example.movierating.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.example.movierating.data.internet.MovieResult
import retrofit2.Response

interface MovieRatingRepository {
    suspend fun loadData(page: Int) : List<MovieResult>?
    fun addMovies(movies: List<MovieResult>)
    fun getMoviesData(): LiveData<List<MovieResult>>
    fun deleteOllMovies()
}