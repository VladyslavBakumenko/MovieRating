package com.example.movierating.data.repositoriesImpl

import android.app.Application
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase


class UserRepositoryImpl() : UserRepository {

    private val application = Application()
    private val db = AppDataBase.getInstance(application)
    private val usersDatabaseDao = db.usersDatabaseDao()

    //@Inject
    //lateinit var db: AppDataBase

    override fun getUsersFromDatabase(): Array<UsersDatabase> {
        return db.usersDatabaseDao().getOllUsersRegistrationInfo()
    }

    override fun addUserToDatabase(user: UsersDatabase) {
        db.usersDatabaseDao().addUser(user)
    }
}