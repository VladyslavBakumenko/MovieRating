package com.example.movierating.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.domain.MovieItem
import com.example.movierating.domain.MovieRatingRepositiry
import com.example.movierating.presentation.ui.test.TestActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

object MovieRatingRepositoryImpl : MovieRatingRepositiry {

    private val movieItemLD = MutableLiveData<List<MovieItem>>()
    private val movieList = sortedSetOf<MovieItem>({ o1, o2 -> o1.id.compareTo(o2.id) })


    init {
        for (i in 0 until MovieRatingRepositiry.TOTAL_MOVIE) {
            val item = MovieItem(i, "$i", "$i", "$i", i.toDouble(), " ")
            addMovieItem(item)
        }
    }

    override fun addMovieItem(movieItem: MovieItem) {
        movieList.add(movieItem)
        updateList()
    }

    override fun getMovieList(): LiveData<List<MovieItem>> {
        return movieItemLD
    }


    override fun checkEmailOnValid(eMail: String): Boolean {
        val validEMailAddress = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        var result = false
        if (validEMailAddress.matches(eMail)) {
            result = true
        }
        return result
    }

    override fun checkPasswordOnValid(password: String): Boolean {
        var result = false
        if (password.length in 4..20) {
            result = true
        }
        return result
    }

    override fun validateInput(eMail: String, password: String): Boolean {
        var result: Int = MovieRatingRepositiry.RETURN_FALSE_IF_FIELDS_INVALID

        if (checkEmailOnValid(eMail)) {
            result++
        }
        if (checkPasswordOnValid(password)) {
            result++
        }
        return result == MovieRatingRepositiry.RETURN_TRUE_IF_FIELDS_VALID
    }

    private fun updateList() {
        movieItemLD.value = movieList.toList()
    }

    private fun loadData() {
        val disposable = ApiFactory.movieApi.getMovie(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Test_OF_LOAD_DATA", it.toString())
            }, {
                var error: String? = it.message
                var errorMessage: String = ""
                error?.let {
                    errorMessage = error
                }
                Log.d("Test_OF_LOAD_DATA", errorMessage)
            })
    }




}