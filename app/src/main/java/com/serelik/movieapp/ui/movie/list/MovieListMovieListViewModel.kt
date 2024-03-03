package com.serelik.movieapp.ui.movie.list

import android.content.Context
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MoviePagingSource
import com.serelik.movieapp.ui.movie.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MovieListMovieListViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : BaseMovieListViewModel(
    movieApiService,
    movieMapper,
    genresStorage,
    context
) {

    fun getMovies(movieListType: MovieListSpecific) {
        val pagingSource = MoviePagingSource(
            movieApiService,
            movieMapper,
            movieListType,
            genresStorage
        )

        handlePagingSource(pagingSource)
    }
}
