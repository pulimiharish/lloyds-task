package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.mapper.FilmDetailApiToDomainMapper
import com.lbg.data.mapper.FilmVideoApiToDomainMapper
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import com.lbg.domain.shared.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmVideoDataSourceImpl @Inject constructor(
    private val service: FilmService,
    private val filmVideoApiToDomainMapper: FilmVideoApiToDomainMapper,
    private val apiResponseHandler: ApiResponseHandler
) : FilmVideoDataSource {

    override suspend fun getVideo(id: Int): Flow<Result<Video>> {
        return flow {
            try {
                val response = service.getMovieVideos(id)
                apiResponseHandler.handleApiResponse(
                    response,
                    onSuccess = {
                        emit(Result.Success(filmVideoApiToDomainMapper.map(it)))
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