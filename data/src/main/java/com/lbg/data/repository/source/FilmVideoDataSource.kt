package com.lbg.data.repository.source

import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import com.lbg.domain.shared.Result
import kotlinx.coroutines.flow.Flow

interface FilmVideoDataSource {
    suspend fun getVideo(id: Int): Flow<Result<Video>>
}