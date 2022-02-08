package com.example.movierating.data.hilt.di

import com.example.movierating.presentation.ui.recyclerViews.linealRv.MovieListLinealAdapter
import com.example.movierating.presentation.ui.recyclerViews.tableRv.MovieListTableAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecyclerViewAdaptersModule {

    @Provides
    @Singleton
    fun provideMovieListLinealAdapter(): MovieListLinealAdapter = MovieListLinealAdapter()

    @Provides
    @Singleton
    fun provideMovieListTableAdapter(): MovieListTableAdapter = MovieListTableAdapter()
}