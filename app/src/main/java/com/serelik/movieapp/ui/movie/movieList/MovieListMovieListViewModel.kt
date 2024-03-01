package com.serelik.movieapp.ui.movie.movieList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MoviePagingSource
import com.serelik.movieapp.ui.movie.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
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

    init {
        viewModelScope.launch {
            getFavoriteMovies()
        }
    }

    fun getMovies(movieListType: MovieListSpecific): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 40),
        pagingSourceFactory = {
            MoviePagingSource(
                movieApiService,
                movieMapper,
                movieListType,
                genresStorage
            )
        }
    ).liveData
}
