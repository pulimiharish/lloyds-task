package com.lbg.film.navigation

object FilmDestinations {
    const val FILMS_LIST_ROUTE = "films_list"
    const val FILM_DETAILS_ROUTE = "film_details/{id}"
    const val FILM_VIDEO_ROUTE = "film_video/{id}"

    object FilmDetailsArgs {
        const val FILM_ID = "id"
    }
}

fun filmDetailsRoute(id: Long) = "film_details/$id"
fun filmVideoRoute(id: Long) = "film_video/$id"