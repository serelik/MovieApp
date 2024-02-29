package com.serelik.movieapp.ui.movie.movieSearch

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MovieSearchPagingSource
import com.serelik.movieapp.ui.movie.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val movieApiService: MovieDBApi,
    val movieMapper: MovieMapper,
    val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : BaseViewModel(movieApiService, movieMapper, genresStorage, context) {

    private val searchMutableLiveData = MutableLiveData<PagingData<Movie>>()
    val searchLiveData: LiveData<PagingData<Movie>> = searchMutableLiveData

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
            ).flow.cachedIn(viewModelScope).collectLatest { searchMutableLiveData.postValue(it) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}
