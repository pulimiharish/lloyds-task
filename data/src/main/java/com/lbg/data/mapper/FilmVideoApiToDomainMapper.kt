package com.lbg.data.mapper

import com.lbg.data.BuildConfig
import com.lbg.data.model.ApiFilm
import com.lbg.data.model.ApiVideo
import com.lbg.data.model.ApiVideoResponse
import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import javax.inject.Inject

class FilmVideoApiToDomainMapper @Inject constructor() :
    DataMapper<ApiVideoResponse, Video> {

    override fun map(data: ApiVideoResponse): Video {
        val trailerVideo = data.results.firstOrNull { it.type == "Trailer" }

        return if (trailerVideo != null) {
            Video(
                key = trailerVideo.key
            )
        } else {
            Video()
        }
    }
}