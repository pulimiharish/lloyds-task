package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.mapper.FilmDetailApiToDomainMapper
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmDetailsDataSourceImpl @Inject constructor(
    private val service: FilmService,
    private val filmDetailApiToDomainMapper: FilmDetailApiToDomainMapper,
    private val apiResponseHandler: ApiResponseHandler
) : FilmDetailsDataSource {

    override suspend fun getFilm(id: Long): Flow<Result<Film>> {
        return flow {
            try {
                val response = service.getMovie(id)
                apiResponseHandler.handleApiResponse(
                    response,
                    onSuccess = {
                        emit(Result.Success(filmDetailApiToDomainMapper.map(it)))
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