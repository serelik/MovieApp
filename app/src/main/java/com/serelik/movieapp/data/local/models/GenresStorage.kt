package com.serelik.movieapp.data.local.models

import com.serelik.movieapp.data.network.MovieDBApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresStorage @Inject constructor(
    private val movieApiService: MovieDBApi
) {

    private var genresIds: Map<Int, String>? = null

    suspend fun getGenres(): Map<Int, String> {
        if (genresIds == null) {
            val genreResponse = movieApiService.getGenresId()
            genresIds = genreResponse.genres.associateBy({ it.id }, { it.name })
        }

        return genresIds.orEmpty()
    }
}
