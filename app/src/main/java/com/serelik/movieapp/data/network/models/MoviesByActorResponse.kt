package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesByActorResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("title")
    val title: String
)
