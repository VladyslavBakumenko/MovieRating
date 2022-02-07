package com.example.movierating.data.hilt.di

import com.example.movierating.data.repositoriesImpl.MovieRatingRepository
import com.example.movierating.data.repositoriesImpl.MovieRatingRepositoryImpl
import com.example.movierating.data.repositoriesImpl.UserRepository
import com.example.movierating.data.repositoriesImpl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository


    @Binds
    fun provideMovieRepository(movieRepositoryImpl: MovieRatingRepositoryImpl): MovieRatingRepository

}