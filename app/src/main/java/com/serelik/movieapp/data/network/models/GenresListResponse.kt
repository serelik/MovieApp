package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresListResponse(
    @SerialName("genres")
    val genres: List<GenreResponse>
)
