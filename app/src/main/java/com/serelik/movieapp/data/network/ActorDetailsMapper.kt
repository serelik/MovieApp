package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.ActorDetails
import com.serelik.movieapp.data.network.models.ActorDetailsResponse
import javax.inject.Inject

class ActorDetailsMapper @Inject constructor(
    private val mapper: ImageMapper
) {

    fun parseActorDetailsResponse(apiModel: ActorDetailsResponse): ActorDetails {
        return ActorDetails(
            id = apiModel.id,
            actorName = apiModel.actorName,
            biography = apiModel.biography,
            profilePicture = mapper.createImageUrl(apiModel.profilePath),
            birthPlace = apiModel.birthPlace,
            birthday = apiModel.birthday.orEmpty(),
            knownFor = apiModel.knownFor
        )
    }
}
