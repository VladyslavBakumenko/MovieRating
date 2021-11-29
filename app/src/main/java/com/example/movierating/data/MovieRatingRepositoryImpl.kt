package com.example.movierating.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MoviePages
import com.example.movierating.domain.FormattedTotalMovieData
import com.example.movierating.domain.MovieItem
import com.example.movierating.domain.MovieRatingRepositiry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object MovieRatingRepositoryImpl : MovieRatingRepositiry {

    private val movieListData = ArrayList<MoviePages>()
    private var moviesData: FormattedTotalMovieData

    private val formattedTotalTitles = mutableListOf<String?>()
    private val formattedTotalBackdropPaths = mutableListOf<String?>()
    private val formattedTotalOriginalLanguages = mutableListOf<String?>()
    private val formattedTotalOverviews = mutableListOf<String?>()
    private val formattedTotalReleaseDates = mutableListOf<String?>()
    private val formattedTotalPopularity = mutableListOf<Double?>()
    private val formattedTotalAverage = mutableListOf<Double?>()
    private val formattedPosterPatches = mutableListOf<String?>()

    private val movieItemLD = MutableLiveData<List<MovieItem>>()
    private val movieList = mutableListOf<MovieItem>()



    init {
        for(i in 1..MovieRatingRepositiry.LOAD_PAGES){
            loadData(i)
        }
        formattedData()
        Log.d("fddfdbcvfgfd", formattedTotalAverage.size.toString())
        moviesData = FormattedTotalMovieData(
            formattedTotalTitles,
            formattedTotalBackdropPaths,
            formattedTotalOriginalLanguages,
            formattedTotalOverviews,
            formattedTotalReleaseDates,
            formattedTotalPopularity,
            formattedTotalAverage,
            formattedPosterPatches)
    }

    override fun getMoviesDataUseCase(): FormattedTotalMovieData {
        return moviesData
    }

    private fun loadData(page: Int){
        val api = ApiFactory.movieApi
        val moviesData = api.getMovie(page = page)
            .subscribeOn(Schedulers.io())
        //   .observeOn(AndroidSchedulers.mainThread())
        movieListData.add(moviesData.blockingGet())
    }

    private fun formattedData() {
        movieListData.map {
            val pageResult = it.results

            pageResult?.let {
                for (i in pageResult) {
                    formattedTotalTitles.add(i.title)
                    formattedTotalBackdropPaths.add(i.backdropPath)
                    formattedTotalOriginalLanguages.add(i.originalLanguage)
                    formattedTotalOverviews.add(i.overview)
                    formattedTotalReleaseDates.add(i.releaseDate)
                    formattedTotalPopularity.add(i.popularity)
                    formattedTotalAverage.add(i.voteAverage)
                    formattedPosterPatches.add(i.posterPath)
                }
            }
        }
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


    private fun lnoadData(page: Int) {
        val disposable = ApiFactory.movieApi.getMovie(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Test_OF_LOAD_DATA", it.toString())
            }, {
                val error: String? = it.message
                var errorMessage: String = ""
                error?.let {
                    errorMessage = error
                }
                Log.d("Test_OF_LOAD_DATA", errorMessage)
            })

    }




}