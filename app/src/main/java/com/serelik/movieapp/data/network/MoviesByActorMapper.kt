package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.MovieByActor
import com.serelik.movieapp.data.network.models.MoviesByActorResponse
import javax.inject.Inject

class MoviesByActorMapper @Inject constructor(
    private val mapper: ImageMapper
) {
    fun parseMovieByActorResponse(apiModel: MoviesByActorResponse): MovieByActor {
        return MovieByActor(
            id = apiModel.id,
            posterPath = mapper.createImageUrl(apiModel.posterPath),
            title = apiModel.title
        )
    }

}