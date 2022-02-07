package com.example.movierating.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRatingRepositoryImpl @Inject constructor(
    private val db: AppDataBase) : MovieRatingRepository {

    private val coroutineScopeIO = CoroutineScope(Dispatchers.IO)

    override fun loadData() {
        coroutineScopeIO.launch {
            db.moviesDatabaseDao().deleteOllMovies()
            for (page in 1..LOAD_PAGES) {
                val moviePage = ApiFactory.movieApi.getMovie(page = page).map { it.results }
                moviePage.blockingGet()?.let { db.moviesDatabaseDao().addMoviesToDatabase(it) }
            }
        }
    }


    override fun getMoviesData(): LiveData<List<MovieResult>> {
        return db.moviesDatabaseDao().getMoviesFromDatabase()
    }

    companion object {
        private const val LOAD_PAGES = 100
    }
}