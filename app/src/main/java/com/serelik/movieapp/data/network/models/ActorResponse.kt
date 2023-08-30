package com.serelik.movieapp.data.network.models

import com.serelik.movieapp.data.local.models.Actor
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ActorResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val picture: String?
) {

    fun parseActorResponse(): Actor {

        return Actor(
            id = id,
            name = name,
            picture = picture?.let { "$baseImageUrl$picture" }
        )
    }

    companion object {
        private const val baseImageUrl = "https://image.tmdb.org/t/p/original"
    }
}