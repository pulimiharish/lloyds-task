package com.lbg.domain.repository

import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import com.lbg.domain.shared.Result
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilmsList(page: Int = 1): Flow<Result<List<Film>>>
    suspend fun getFilm(id: Long): Flow<Result<Film>>
    suspend fun getVideo(id: Int): Flow<Result<Video>>
}