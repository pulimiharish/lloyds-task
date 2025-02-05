package com.lbg.film.state

import com.lbg.domain.model.Film

data class FilmsPaginationState(
    val currentPage: Int = 1,
    val isLoadingMore: Boolean = false,
    val films: List<Film> = emptyList()
)
