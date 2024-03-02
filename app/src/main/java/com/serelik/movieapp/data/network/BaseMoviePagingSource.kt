package com.serelik.movieapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.models.MovieListResponse

abstract class BaseMoviePagingSource(
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    protected abstract suspend fun getMovies(pageNumber: Int): MovieListResponse

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1

            val movieInfo = getMovies(pageNumber)

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
