package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ActorsListResponse(
    @SerialName("cast")
    val cast: List<ActorResponse>
)