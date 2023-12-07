package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieListResponse(
    @SerialName("results")
    val movies: List<MovieResponse>
)
