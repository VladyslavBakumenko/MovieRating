package com.example.movierating.data.hilt.di

import com.example.movierating.data.repositoriesImpl.MovieRatingRepositoryImpl
import com.example.movierating.data.repositoriesImpl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepositoryImpl = UserRepositoryImpl()



    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRatingRepositoryImpl = MovieRatingRepositoryImpl()
}