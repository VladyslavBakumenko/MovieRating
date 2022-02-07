package com.example.movierating.data.repositoriesImpl

import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val db: AppDataBase) : UserRepository {


    override fun getUsersFromDatabase(): Array<UsersDatabase> {
        return db.usersDatabaseDao().getOllUsersRegistrationInfo()
    }

    override fun addUserToDatabase(user: UsersDatabase) {
        db.usersDatabaseDao().addUser(user)
    }
}