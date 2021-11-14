package com.example.movierating.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_registration_info")
data class UsersDatabase(
    @PrimaryKey()
    var eMail: String,

    var password: String
)

