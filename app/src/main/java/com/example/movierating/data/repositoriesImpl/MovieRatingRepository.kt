package com.example.movierating.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.example.movierating.data.internet.MovieResult

interface MovieRatingRepository {
    fun loadData()
    fun getMoviesData(): LiveData<List<MovieResult>>
}