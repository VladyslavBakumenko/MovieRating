package com.example.movierating.data.hilt.di

import android.content.Context
import com.example.movierating.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
            : AppDataBase = AppDataBase.getInstance(context)

}