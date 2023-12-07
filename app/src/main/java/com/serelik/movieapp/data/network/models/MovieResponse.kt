package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieResponse(
    @SerialName("poster_path")
    val posterPathListMovieImage: String?,
    @SerialName("overview")
    val overview: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPathMovieDetailsImage: String?,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
)
