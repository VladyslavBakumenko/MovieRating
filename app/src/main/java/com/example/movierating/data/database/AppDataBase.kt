package com.example.movierating.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movierating.data.internet.MovieResult

@Database(entities = [UsersDatabase::class, MovieResult::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun usersDatabaseDao(): UsersDataBaseDao
    abstract fun moviesDatabaseDao(): MoviesDatabaseDao

    companion object {

        private var db: AppDataBase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }
}