package com.lbg.data.di

import com.lbg.data.repository.source.FilmDetailsDataSource
import com.lbg.data.repository.source.FilmDetailsDataSourceImpl
import com.lbg.data.repository.source.FilmVideoDataSource
import com.lbg.data.repository.source.FilmVideoDataSourceImpl
import com.lbg.data.repository.source.FilmsListDataSource
import com.lbg.data.repository.source.FilmsListDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindFilmMapDataSource(filmsListDataSourceImpl: FilmsListDataSourceImpl):
            FilmsListDataSource

    @Binds
    abstract fun bindFilmDetailsDataSource(filmDetailsDataSourceImpl: FilmDetailsDataSourceImpl):
            FilmDetailsDataSource

    @Binds
    abstract fun bindFilmVideoDataSource(filmVideoDataSourceImpl: FilmVideoDataSourceImpl):
            FilmVideoDataSource
}