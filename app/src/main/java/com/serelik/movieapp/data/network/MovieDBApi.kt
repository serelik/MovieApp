package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.network.models.ActorDetailsResponse
import com.serelik.movieapp.data.network.models.ActorsListResponse
import com.serelik.movieapp.data.network.models.DetailsMovieResponse
import com.serelik.movieapp.data.network.models.GenresListResponse
import com.serelik.movieapp.data.network.models.MovieListResponse
import com.serelik.movieapp.data.network.models.MoviesByActorListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("genre/movie/list")
    suspend fun getGenresId(): GenresListResponse

    @GET("movie/{specific}")
    suspend fun getMoviesList(
        @Path(value = "specific") key: String,
        @Query("page") page: Int
    ): MovieListResponse

    @GET("search/movie")
    suspend fun getSearchMovieList(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path(value = "movie_id") key: Int
    ): DetailsMovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path(value = "movie_id") key: Int
    ): ActorsListResponse

    @GET("person/{actor_id}")
    suspend fun getActor(
        @Path(value = "actor_id") key: Int
    ): ActorDetailsResponse

    @GET("person/{actor_id}/movie_credits")
    suspend fun getMoviesByActor(
        @Path(value = "actor_id") key: Int
    ): MoviesByActorListResponse
}
