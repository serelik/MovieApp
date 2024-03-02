package com.serelik.movieapp.ui.movie.movieList

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.local.models.MovieUI
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MoviePagingSource
import com.serelik.movieapp.ui.movie.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
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

    fun getMovies(movieListType: MovieListSpecific) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10, maxSize = 40),
                pagingSourceFactory = {
                    MoviePagingSource(
                        movieApiService,
                        movieMapper,
                        movieListType,
                        genresStorage
                    )
                }
            ).flow.cachedIn(viewModelScope).map {
                it.map { movie ->
                    MovieUI(movie, isFavorite(movie.id))
                }
            }.collectLatest { movieMutableLiveData.postValue(it) }
        }
    }
}
