package com.example.movierating.domain


interface MovieRatingRepositiry {
    fun checkEmailOnValid(eMail: String): Boolean
    fun checkPasswordOnValid(password: String): Boolean
    fun validateInput(eMail: String, password: String): Boolean
    fun getMovieLinealList(): List<MovieItem>
    fun getMovieTableList(): List<MovieItem>
    fun getMoviesDataUseCase(): FormattedTotalMovieData

    companion object {
        const val RETURN_FALSE_IF_FIELDS_INVALID = -1
        const val RETURN_TRUE_IF_FIELDS_VALID = 1

        private const val MOVIE_IN_ONE_PAGE = 20
        private const val NUMBER_MOVIE_ITEMS_IN_ONE_TABLE_ITEM = 3
        const val LOAD_PAGES = 30

        const val NUMBER_LINEAL_ITEMS = MOVIE_IN_ONE_PAGE * LOAD_PAGES
        const val NUMBER_TABLE_ITEMS =
            MOVIE_IN_ONE_PAGE * LOAD_PAGES / NUMBER_MOVIE_ITEMS_IN_ONE_TABLE_ITEM
    }
}
