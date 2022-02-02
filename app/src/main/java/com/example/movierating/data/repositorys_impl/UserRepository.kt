package com.example.movierating.data.repositorys_impl

import com.example.movierating.data.database.UsersDatabase

interface UserRepository {
    fun getUsersFromDatabase(): Array<UsersDatabase>
    fun addUserToDatabase(user: UsersDatabase)
}