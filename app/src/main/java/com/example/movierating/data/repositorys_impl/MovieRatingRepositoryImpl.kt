package com.example.movierating.data.repositorys_impl

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieRatingRepositoryImpl : MovieRatingRepository {

    private val application = Application()
    private val db = AppDataBase.getInstance(application)
    private val moviesDatabaseDao = db.moviesDatabaseDao()

    private val coroutineScopeIO = CoroutineScope(Dispatchers.IO)


    override fun loadData() {
        coroutineScopeIO.launch {
            moviesDatabaseDao.deleteOllMovies()
            for (page in 1..LOAD_PAGES) {
                val moviePage = ApiFactory.movieApi.getMovie(page = page).map { it.results }
                moviePage.blockingGet()?.let { moviesDatabaseDao.addMoviesToDatabase(it) }
            }
        }
    }


   override fun getMoviesData(): LiveData<List<MovieResult>> {
        return moviesDatabaseDao.getMoviesFromDatabase()
    }



    companion object {
        private const val LOAD_PAGES = 100
    }


}