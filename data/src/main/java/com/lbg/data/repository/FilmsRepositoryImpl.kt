package com.lbg.data.repository

import com.lbg.data.repository.source.FilmDetailsDataSource
import com.lbg.data.repository.source.FilmVideoDataSource
import com.lbg.data.repository.source.FilmsListDataSource
import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import com.lbg.domain.repository.FilmsRepository
import com.lbg.domain.shared.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsListDataSource: FilmsListDataSource,
    private val filmDetailsDataSource: FilmDetailsDataSource,
    private val filmVideoDataSource: FilmVideoDataSource
) : FilmsRepository {
    override suspend fun getFilmsList(page: Int): Flow<Result<List<Film>>> =
        filmsListDataSource.getFilmsList(page)


    override suspend fun getFilm(id: Long): Flow<Result<Film>> =
        filmDetailsDataSource.getFilm(id)

    override suspend fun getVideo(id: Int): Flow<Result<Video>> =
        filmVideoDataSource.getVideo(id)
}