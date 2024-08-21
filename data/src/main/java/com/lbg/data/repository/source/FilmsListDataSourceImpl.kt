package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.mapper.FilmsListApiToDomainMapper
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmsListDataSourceImpl @Inject constructor(
    private val service: FilmService,
    private val filmsListApiToDomainMapper: FilmsListApiToDomainMapper,
    private val apiResponseHandler: ApiResponseHandler
) : FilmsListDataSource {
    override suspend fun getFilmsList(page: Int): Flow<Result<List<Film>>> {
        return flow {
            try {
                val response = service.getMoviesList(page)
                apiResponseHandler.handleApiResponse(response,
                    onSuccess = {
                        emit(Result.Success(filmsListApiToDomainMapper.map(it)))
                    }, onError = {
                        emit(Result.Error(IllegalArgumentException("response failed")))
                    }
                )
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}