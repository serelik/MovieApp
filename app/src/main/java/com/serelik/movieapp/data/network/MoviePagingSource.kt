package com.serelik.movieapp.data.network

import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.network.models.MovieListResponse

class MoviePagingSource(
    private val movieApiService: MovieDBApi,
    movieMapper: MovieMapper,
    private val movieListType: MovieListSpecific,
    genresStorage: GenresStorage
) : BaseMoviePagingSource(genresStorage = genresStorage, movieMapper = movieMapper) {

    override suspend fun getMovies(pageNumber: Int): MovieListResponse {
        return movieApiService.getMoviesList(movieListType.remotePath, pageNumber)
    }
}
