package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.network.models.ActorResponse
import javax.inject.Inject

class ActorMapper @Inject constructor(
    private val mapper: ImageMapper
) {
    fun parseActorResponse(apiModel: ActorResponse): Actor {
        return Actor(
            id = apiModel.id,
            name = apiModel.name,
            picture = mapper.createImageUrl(apiModel.picture)
        )
    }
}
