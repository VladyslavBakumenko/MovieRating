package com.example.movierating.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movierating.data.internet.MovieResult

@Dao
interface MoviesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMoviesToDatabase(movieResult: List<MovieResult>)

    @Query("SELECT * FROM movies_info")
    fun getMoviesFromDatabase(): LiveData<List<MovieResult>>

    @Query("DELETE from movies_info")
    fun deleteOllMovies()
}