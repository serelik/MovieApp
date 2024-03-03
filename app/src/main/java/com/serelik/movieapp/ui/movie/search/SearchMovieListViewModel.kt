package com.serelik.movieapp.ui.movie.search

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MovieSearchPagingSource
import com.serelik.movieapp.ui.movie.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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

    private fun getMovies(query: String) {
        val pagingSource = MovieSearchPagingSource(
            movieApiService,
            movieMapper,
            query,
            genresStorage
        )

        handlePagingSource(pagingSource)
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}
