package com.lbg.data.repository.source

import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import kotlinx.coroutines.flow.Flow

interface FilmDetailsDataSource {
    suspend fun getFilm(id: Long): Flow<Result<Film>>
}