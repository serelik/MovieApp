package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesByActorListResponse(
    @SerialName("cast")
    val movies: List<MoviesByActorResponse>
)
