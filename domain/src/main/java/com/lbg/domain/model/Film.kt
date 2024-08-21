package com.lbg.domain.model

data class Film(
    val id: Long,
    val title: String,
    val overview: String,
    val image: String,
    val voteAverage: String?,
)