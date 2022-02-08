package com.example.movierating.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRatingRepositoryImpl @Inject constructor(
    private val db: AppDataBase) : MovieRatingRepository {

    override suspend fun loadData(page: Int) : List<MovieResult>? {
        return ApiFactory.movieApi.getMovie(page = page).body()?.results
    }

    override fun addMovies(movies: List<MovieResult>) {
        db.moviesDatabaseDao().addMoviesToDatabase(movies)
    }

    override fun getMoviesData(): LiveData<List<MovieResult>> {
        return db.moviesDatabaseDao().getMoviesFromDatabase()
    }

    override fun deleteOllMovies() {
        db.moviesDatabaseDao().deleteOllMovies()
    }

}