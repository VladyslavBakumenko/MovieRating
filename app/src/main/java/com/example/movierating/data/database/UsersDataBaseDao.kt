package com.example.movierating.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movierating.data.internet.MoviePages

@Dao
interface UsersDataBaseDao {
    @Insert
    fun addUser(userRegistrationInfo: UsersDatabase)

    @Query("SELECT * FROM user_registration_info")
    fun getOllUsersRegistrationInfo(): Array<UsersDatabase>
}