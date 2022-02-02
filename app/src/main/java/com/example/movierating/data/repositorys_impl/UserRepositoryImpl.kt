package com.example.movierating.data.repositorys_impl

import android.app.Application
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase

class UserRepositoryImpl: UserRepository {

    private val application = Application()
    private val db = AppDataBase.getInstance(application)
    private val usersDatabaseDao = db.usersDatabaseDao()


    override fun getUsersFromDatabase(): Array<UsersDatabase> {
        return usersDatabaseDao.getOllUsersRegistrationInfo()
    }

    override fun addUserToDatabase(user: UsersDatabase) {
        usersDatabaseDao.addUser(user)
    }
}