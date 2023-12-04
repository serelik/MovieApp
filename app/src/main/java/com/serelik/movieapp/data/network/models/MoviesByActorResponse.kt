package com.serelik.movieapp.data.network.models

import com.serelik.movieapp.data.local.models.MovieByActor
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
) {
    fun parseMovieByActorResponse(): MovieByActor {
        return MovieByActor(
            id = id,
            posterPath = posterPath.let { "${baseImageUrl}$posterPath" },
            title = title
        )
    }

    companion object {
        private const val baseImageUrl = "https://image.tmdb.org/t/p/original"
    }
}