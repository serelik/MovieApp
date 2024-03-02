package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.network.models.MovieListResponse

class MovieSearchPagingSource(
    private val movieApiService: MovieDBApi,
    movieMapper: MovieMapper,
    private val query: String,
    genresStorage: GenresStorage
) : BaseMoviePagingSource(genresStorage = genresStorage, movieMapper = movieMapper) {

    override suspend fun getMovies(pageNumber: Int): MovieListResponse {
        return movieApiService.getSearchMovieList(query, pageNumber)
    }
}
