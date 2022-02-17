package com.example.movierating.data.hilt.di.sharedPreferanceModules

import com.example.movierating.data.sharedPreferencesManager.ISharedPreferencesManager
import com.example.movierating.data.sharedPreferencesManager.SharedPreferencesManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferenceManagerModule {

    @Binds
    fun provideSharedPreferenceManager(sharedPreferenceManager: SharedPreferencesManager)
    : ISharedPreferencesManager
}