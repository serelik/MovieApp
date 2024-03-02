package com.serelik.movieapp.ui.movie.movieSearch

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.MovieUI
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MovieSearchPagingSource
import com.serelik.movieapp.ui.movie.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieListViewModel @Inject constructor(
    val movieApiService: MovieDBApi,
    val movieMapper: MovieMapper,
    val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : BaseMovieListViewModel(movieApiService, movieMapper, genresStorage, context) {

    private val searchFlow = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            getFavoriteMovies()
        }
        viewModelScope.launch {
            searchFlow
                .debounce(SEARCH_DEBOUNCE)
                .collectLatest {
                    getMovies(it)
                }
        }
    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            searchFlow.emit(query)
        }
    }

    fun getMovies(query: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10, maxSize = 40),
                pagingSourceFactory = {
                    MovieSearchPagingSource(
                        movieApiService,
                        movieMapper,
                        query,
                        genresStorage
                    )
                }
            ).flow.cachedIn(viewModelScope)
                .map {
                    it.map { movie ->
                        MovieUI(movie, isFavorite(movie.id))
                    }
                }.collectLatest { movieMutableLiveData.postValue(it) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}
