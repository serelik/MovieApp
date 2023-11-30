package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.models.MovieResponse
import javax.inject.Inject

class MovieMapper @Inject constructor(
    private val mapper: ImageMapper
) {
    fun parseMovieResponse(genres: Map<Int, String>, apiModel: MovieResponse): Movie {

       val movieGenres = apiModel.genreIds.mapNotNull { genres[it] }.joinToString(separator = ", ")

        return Movie(
            id = apiModel.id,
            pg = if (apiModel.adult) "18+" else "13+",
            genres = movieGenres,
            rating = apiModel.voteAverage / 2.0f,
            reviews = apiModel.voteCount,
            name = apiModel.title,
            backdropMovieDetailsImageUrl = mapper.createImageUrl(apiModel.backdropPathMovieDetailsImage),
            backPosterMainMovieImageUrl = mapper.createImageUrl(apiModel.posterPathListMovieImage),
            overview = apiModel.overview,
        )
    }

}