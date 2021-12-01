package com.example.movierating.data.repositorys_impl

import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.data.internet.MoviePages
import com.example.movierating.domain.FormattedTotalMovieData
import com.example.movierating.domain.MovieItem
import com.example.movierating.domain.MovieRatingRepositiry
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

    private val movieLinealList = mutableListOf<MovieItem>()
    private val movieTableList = mutableListOf<MovieItem>()


    init {
        for (i in 1..MovieRatingRepositiry.LOAD_PAGES) {
            loadData(i)
        }
        formattedData()

        moviesData = FormattedTotalMovieData(
            formattedTotalTitles,
            formattedTotalBackdropPaths,
            formattedTotalOriginalLanguages,
            formattedTotalOverviews,
            formattedTotalReleaseDates,
            formattedTotalPopularity,
            formattedTotalAverage,
            formattedPosterPatches
        )

        for (i in 0 until MovieRatingRepositiry.NUMBER_LINEAL_ITEMS) {
            val item = MovieItem()
            movieLinealList.add(item)
        }

        for (i in 0 until MovieRatingRepositiry.NUMBER_TABLE_ITEMS) {
            val item = MovieItem()
            movieTableList.add(item)
        }
    }


    override fun getMoviesDataUseCase(): FormattedTotalMovieData {
        return moviesData
    }

    private fun loadData(page: Int) {
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

    override fun getMovieTableList(): List<MovieItem> {
        return movieTableList
    }

    override fun getMovieLinealList(): List<MovieItem> {
        return movieLinealList
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

}