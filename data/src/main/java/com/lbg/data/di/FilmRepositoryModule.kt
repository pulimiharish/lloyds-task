package com.lbg.data.di

import com.lbg.data.repository.FilmsRepositoryImpl
import com.lbg.domain.repository.FilmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FilmRepositoryModule {

    @Binds
    abstract fun bindFilmRepo(filmsRepositoryImpl: FilmsRepositoryImpl): FilmsRepository
}
