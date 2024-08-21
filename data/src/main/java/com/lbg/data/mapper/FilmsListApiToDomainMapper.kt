package com.lbg.data.mapper

import com.lbg.data.BuildConfig
import com.lbg.data.model.ApiFilm
import com.lbg.data.model.ApiFilmListResponse
import com.lbg.domain.model.Film
import javax.inject.Inject

class FilmsListApiToDomainMapper @Inject constructor() :
    DataMapper<ApiFilmListResponse, List<Film>> {

    override fun map(data: ApiFilmListResponse): List<Film> {
        val filmsListData = data.results.map { apiFilm ->
            mapInternals(apiFilm)
        }
        return filmsListData
    }

    private fun mapInternals(data: ApiFilm): Film {
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
