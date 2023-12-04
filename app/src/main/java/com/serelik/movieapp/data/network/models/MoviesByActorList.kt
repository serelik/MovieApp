package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesByActorList(
    @SerialName("cast")
    val movies: List<MoviesByActorResponse>
)