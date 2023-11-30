package com.serelik.movieapp.data.network.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ActorResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val picture: String?
)