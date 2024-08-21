package com.lbg.film

import com.lbg.domain.model.Film
import com.lbg.domain.model.Video

fun mockFilmsList(): List<Film> {
    return listOf(
        mockFilm()
    )
}

fun mocknextFilmsList(): List<Film> {
    return listOf(
        mockNextFilm()
    )
}

fun mockFilm(): Film {
    return Film(
        id = 123L,
        title = "The Iron Man",
        overview = "This is a movie about Iron man",
        image = "/IronMan.jpg",
        voteAverage = "8.2345"
    )
}

fun mockNextFilm(): Film {
    return Film(
        id = 456L,
        title = "The Iron Man",
        overview = "This is a movie about Iron man",
        image = "/IronMan.jpg",
        voteAverage = "8.2345"
    )
}

fun mockVideo(): Video {
    return Video(
        key = "12345"
    )
}

