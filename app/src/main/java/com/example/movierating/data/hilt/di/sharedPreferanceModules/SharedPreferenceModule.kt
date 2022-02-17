package com.example.movierating.data.hilt.di.sharedPreferanceModules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class SharedPreferenceModule  {

    private val sharedPreferenceUserData: String = "sharedPreferenceUserData"

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(sharedPreferenceUserData, Context.MODE_PRIVATE)

}