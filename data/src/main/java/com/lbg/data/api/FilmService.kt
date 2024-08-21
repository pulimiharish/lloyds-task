package com.lbg.data.api

import com.lbg.data.model.ApiFilm
import com.lbg.data.model.ApiFilmListResponse
import com.lbg.data.model.ApiVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {

    @GET("movie/top_rated")
    suspend fun getMoviesList(
        @Query("page") page: Int = 1
    ): Response<ApiFilmListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Long
    ): Response<ApiFilm>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): Response<ApiVideoResponse>

}