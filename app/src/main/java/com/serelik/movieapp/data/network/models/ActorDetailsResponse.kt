package com.serelik.movieapp.data.network.models

import com.serelik.movieapp.data.local.models.ActorDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val actorName: String,
    @SerialName("biography")
    val biography: String,
    @SerialName("birthday")
    val birthday: String?,
    @SerialName("place_of_birth")
    val birthPlace: String,
    @SerialName("known_for_department")
    val knownFor: String,
    @SerialName("profile_path")
    val profilePath: String?

) {
    fun parseActorResponse(): ActorDetails {
        return ActorDetails(
            id = id,
            actorName = actorName,
            biography = biography,
            profilePicture = profilePath?.let { "${baseImageUrl}$profilePath" },
            birthPlace = birthPlace,
            birthday = birthday.orEmpty(),
            knownFor = knownFor
        )
    }

    companion object {
        private const val baseImageUrl = "https://image.tmdb.org/t/p/original"
    }
}