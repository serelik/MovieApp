package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.network.models.ActorsListResponse
import com.serelik.movieapp.data.network.models.GenresListResponse
import com.serelik.movieapp.data.network.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBApi {
    @GET("genre/movie/list")
    suspend fun getGenresId(): GenresListResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(): MovieListResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path(value = "movie_id") key: Int
    ): ActorsListResponse


}