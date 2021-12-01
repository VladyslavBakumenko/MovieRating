package com.example.movierating.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDataBaseDao {
    @Insert
    fun addUser(userRegistrationInfo: UsersDatabase)

    @Query("SELECT * FROM user_registration_info")
    fun getOllUsersRegistrationInfo(): Array<UsersDatabase>
}