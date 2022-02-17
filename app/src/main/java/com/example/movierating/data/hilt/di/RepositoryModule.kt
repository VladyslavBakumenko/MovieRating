package com.example.movierating.data.hilt.di

import com.example.movierating.data.repositorys.movieRatingRepository.MovieRatingRepository
import com.example.movierating.data.repositorys.movieRatingRepository.MovieRatingRepositoryImpl
import com.example.movierating.data.repositorys.userRepository.UserRepository
import com.example.movierating.data.repositorys.userRepository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideMovieRepository(movieRepositoryImpl: MovieRatingRepositoryImpl): MovieRatingRepository

    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}