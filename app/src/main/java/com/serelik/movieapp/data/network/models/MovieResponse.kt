package com.serelik.movieapp.data.network.models

import com.serelik.movieapp.data.local.models.Movie
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieResponse(
    @SerialName("poster_path")
    val posterPathListMovieImage: String?,
    @SerialName("overview")
    val overview: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPathMovieDetailsImage: String?,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
) {
    fun parseMovieResponse(genres: Map<Int, String>): Movie {
        val sb = StringBuilder()

        for (i in genreIds) {
            if (genres.containsKey(i))
                sb.append("${genres[i]}, ")
        }

        if (sb.isNotEmpty()) {
            sb.delete(sb.length - 2, sb.length)

        }
        return Movie(
            id = id,
            pg = if (adult) "18+" else "13+",
            genres = sb.toString(),
            rating = voteAverage / 2.0f,
            reviews = voteCount,
            name = title,
            backdropMovieDetailsImageUrl = backdropPathMovieDetailsImage?.let { "$baseImageUrl$backdropPathMovieDetailsImage" },
            backPosterMainMovieImageUrl = posterPathListMovieImage?.let { "$baseImageUrl$posterPathListMovieImage" },
            overview = overview,
        )
    }

    companion object {
        private const val baseImageUrl = "https://image.tmdb.org/t/p/original"
    }

}