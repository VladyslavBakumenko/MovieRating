package com.example.movierating.presentation.ui.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val db = AppDataBase.getInstance(application)
    private val moviesDatabaseDao = db.moviesDatabaseDao()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    fun loadData() {
        coroutineScope.launch {
            moviesDatabaseDao.deleteOllMovies()
        }

        for (i in LOAD_PAGES downTo 1) {

            val disposable = ApiFactory.movieApi.getMovie(page = i)
                .map { it.results }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    coroutineScope.launch {
                        moviesDatabaseDao.addMoviesToDatabase(it)
                    }
                }, {

                })
            compositeDisposable.add(disposable)

        }

    }

    fun getMoviesData(): LiveData<List<MovieResult>> {
        return moviesDatabaseDao.getMoviesFromDatabase()
    }

    companion object {
        const val LOAD_PAGES = 100
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}