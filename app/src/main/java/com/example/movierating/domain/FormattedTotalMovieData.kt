package com.example.movierating.domain

data class FormattedTotalMovieData(
    val formattedTotalTitles: MutableList<String?>,
    val formattedTotalBackdropPaths: MutableList<String?>,
    val formattedTotalOriginalLanguages: MutableList<String?>,
    val formattedTotalOverviews: MutableList<String?>,
    val formattedTotalReleaseDates: MutableList<String?>,
    val formattedTotalPopularity: MutableList<Double?>,
    val formattedTotalAverage: MutableList<Double?>,
    val formattedPosterPatches: MutableList<String?>
)
