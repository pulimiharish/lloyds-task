package com.lbg.data.model

import com.google.gson.annotations.SerializedName

data class ApiFilmListResponse(val results: List<ApiFilm>)

data class ApiFilm(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: String?,
    @SerializedName("genres") val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)

data class ApiVideoResponse(
    val id: Int,
    val results: List<ApiVideo>
)

data class ApiVideo(
    @SerializedName("id") val id: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val publishedAt: String
)


