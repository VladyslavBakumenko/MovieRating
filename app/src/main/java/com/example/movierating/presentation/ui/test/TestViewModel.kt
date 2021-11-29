package com.example.movierating.presentation.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.movierating.data.internet.MovieApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TestViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

  //  fun getMovieList(movieApi: MovieApi?) {
  //      movieApi?.let {
  //          compositeDisposable.add(movieApi.getMovieList()
    //            .subscribeOn(Schedulers.io())
    //            .observeOn(AndroidSchedulers.mainThread())
     //           .subscribe({
     //               Log.d("Teeeeeesrrrr", it.results.)
     //           }, {
//
     //           })
   //         )
   //     }


   // }


}