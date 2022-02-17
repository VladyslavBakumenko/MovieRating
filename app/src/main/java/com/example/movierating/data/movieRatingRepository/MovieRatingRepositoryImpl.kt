package com.example.movierating.data.movieRatingRepository

import com.example.movierating.data.internet.api.ApiFactory
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRatingRepositoryImpl @Inject constructor() : MovieRatingRepository {

    override suspend fun loadData(page: Int) : List<MovieResult>? {
        return ApiFactory.movieApi.getMovie(page = page).body()?.results
    }
}