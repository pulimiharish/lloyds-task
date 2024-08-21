package com.lbg.data

import com.google.gson.annotations.SerializedName
import com.lbg.data.model.ApiFilm
import com.lbg.data.model.ApiFilmListResponse
import com.lbg.data.model.ApiVideo
import com.lbg.data.model.ApiVideoResponse
import com.lbg.data.model.Genre
import com.lbg.domain.model.Film
import com.lbg.domain.model.Video
import okhttp3.ResponseBody.Companion.toResponseBody

internal fun mockApiFilmListResponse(): ApiFilmListResponse {
    return ApiFilmListResponse(
        mockApiFilmsList()
    )
}

internal fun mockApiFilmsList(): List<ApiFilm> {
    return listOf(
        mockApiFilm1(),
        mockApiFilm2()
    )
}

internal fun mockApiVideoResponse(): ApiVideoResponse {
    return ApiVideoResponse(
        id = 123,
        results = listOf(mockApiVideo())
    )
}

internal fun mockApiFilm1(
    id: Long = 123L,
    title: String = "Iron Man",
    overview: String = "This is about Iron man the superhero",
    posterPath: String = "/ironman.jpg",
    backdropPath: String = "/ironman3.jpg",
    voteAverage: String = "8.23456",
    genres: List<Genre> = listOf(
        Genre(1, "Hello")
    )
): ApiFilm {
    return ApiFilm(id, title, overview, posterPath, backdropPath, voteAverage, genres)
}

internal fun mockApiFilm2(
    id: Long = 456L,
    title: String = "Iron Man2",
    overview: String = "This is about Iron man2 the superhero",
    posterPath: String = "/ironman2.jpg",
    backdropPath: String = "/ironman4.jpg",
    voteAverage: String = "9.23456",
    genres: List<Genre> = listOf(
        Genre(2, "Hello")
    )
): ApiFilm {
    return ApiFilm(id, title, overview, posterPath, backdropPath, voteAverage, genres)
}

internal fun mockApiVideo(
    id: String = "123",
    iso6391: String = "",
    iso31661: String = "",
    name: String ="",
    key: String = "12345",
    site: String ="",
    size: Int = 1,
    type: String = "Trailer",
    official: Boolean = true,
    publishedAt: String = ""
): ApiVideo {
    return ApiVideo(id, iso6391, iso31661, name, key, site, size, type, official, publishedAt)
}

fun mockFilmsList(): List<Film> {
    return listOf(
        mockFilm1(),
        mockFilm2()
    )
}

fun mockFilm1(): Film {
    return Film(
        id = 123L,
        title = "The Iron Man",
        overview = "This is a movie about Iron man",
        image = "/IronMan.jpg",
        voteAverage = "8.2345"
    )
}

fun mockFilm2(): Film {
    return Film(
        id = 456L,
        title = "The Iron Man2",
        overview = "This is a movie about Iron man2",
        image = "/IronMan2.jpg",
        voteAverage = "9.2345"
    )
}

internal fun emptyResponseBody() = "".toResponseBody(null)

internal fun emptyApiVideoResponse(): ApiVideoResponse {
    return ApiVideoResponse(
        id = 123,
        results = emptyList()
    )
}

internal fun emptyVideo(): Video = Video()

fun mockVideo(): Video {
    return Video(
        key = "12345"
    )
}