package com.lbg.data.repository.source

import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import kotlinx.coroutines.flow.Flow

interface FilmsListDataSource {
    suspend fun getFilmsList(page: Int = 1): Flow<Result<List<Film>>>
}