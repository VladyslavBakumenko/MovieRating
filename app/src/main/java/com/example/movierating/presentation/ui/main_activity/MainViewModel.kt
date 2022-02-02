package com.example.movierating.presentation.ui.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBase.getInstance(application)
    private val moviesDatabaseDao = db.moviesDatabaseDao()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    fun loadData() {
        coroutineScope.launch {
            moviesDatabaseDao.deleteOllMovies()
            for (page in 1..LOAD_PAGES) {
                val moviePage = ApiFactory.movieApi.getMovie(page = page).map { it.results }
                moviePage.blockingGet()?.let { moviesDatabaseDao.addMoviesToDatabase(it) }
            }
        }
    }

    fun getMoviesData(): LiveData<List<MovieResult>> {
        return moviesDatabaseDao.getMoviesFromDatabase()
    }

    companion object {
        const val LOAD_PAGES = 100
    }

}
