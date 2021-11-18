package com.example.movierating.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MovieApi
import com.example.movierating.data.internet.MoviePages
import com.example.movierating.data.internet.MovieResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val PAGE = 1
        const val API_KEY = "a49cf8a5f42225880f049917a2262e81"
        const val LANGUAGE = "en-US"
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var i = 0
        while(i < 500) {
            val disposable = ApiFactory.movieApi.getMovie(page = i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Test_OF_LOAD_DATA", it.toString())
                }, {
                    var error : String? = it.message
                    var errorMessage: String = ""
                    error?.let {
                        errorMessage = error
                    }
                    Log.d("Test_OF_LOAD_DATA", errorMessage)
                })
            compositeDisposable.add(disposable)
            i++
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}