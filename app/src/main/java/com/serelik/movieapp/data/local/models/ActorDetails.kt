package com.serelik.movieapp.data.local.models

data class ActorDetails(
    val id: Int,
    val actorName: String,
    val biography: String?,
    val birthday: String?,
    val birthPlace: String?,
    val knownFor: String,
    val profilePicture: String?
)