package com.serelik.movieapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieListSpecific

class MoviePagingSource(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val movieListType: MovieListSpecific,
    private val genresStorage: GenresStorage
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1

            val movieInfo = movieApiService.getMoviesList(movieListType.remotePath, pageNumber)
            val genres = genresStorage.getGenres()

            val movies = movieInfo.movies.map { movieMapper.parseMovieResponse(genres, it) }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageNumber == 1) null else (pageNumber - 1),
                nextKey = if (pageNumber == movieInfo.totalPages) null else (pageNumber + 1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
