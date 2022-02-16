package com.example.movierating.data.hilt.di

import com.example.movierating.data.repositoriesImpl.movieRatingRepository.MovieRatingRepository
import com.example.movierating.data.repositoriesImpl.movieRatingRepository.MovieRatingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideMovieRepository(movieRepositoryImpl: MovieRatingRepositoryImpl): MovieRatingRepository
}