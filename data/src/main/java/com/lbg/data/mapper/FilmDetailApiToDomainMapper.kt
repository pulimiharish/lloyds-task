package com.lbg.data.mapper

import com.lbg.data.BuildConfig
import com.lbg.data.model.ApiFilm
import com.lbg.domain.model.Film
import javax.inject.Inject

class FilmDetailApiToDomainMapper @Inject constructor() :
    DataMapper<ApiFilm, Film> {

    override fun map(data: ApiFilm): Film {
        data.apply {
            return Film(
                id = id,
                overview = overview,
                title = title,
                voteAverage = voteAverage?.convertToOneDecimal() ?: "",
                image = BuildConfig.IMAGE_URL + posterPath
            )
        }
    }
}