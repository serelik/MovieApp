package com.serelik.movieapp.ui.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieUI
import com.serelik.movieapp.data.network.BaseMoviePagingSource
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MovieSearchPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseMovieListViewModel(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : ViewModel() {
    private val dataBase = FavoritesDataBase.createDataBase(context)

    private val favoritesMutableLiveData = MutableLiveData<List<Favorite>>()
    private val favoritesInfoLiveData: LiveData<List<Favorite>> = favoritesMutableLiveData

    protected val movieMutableLiveData = MutableLiveData<PagingData<MovieUI>>()
    val movieLiveData: LiveData<PagingData<MovieUI>> = movieMutableLiveData

    suspend fun getFavoriteMovies() {
        dataBase.favoriteDao().getAll().collect { it ->
            favoritesMutableLiveData.postValue(it)
            updateFavoriteState()
        }
    }

    fun isFavorite(movieId: Int): Boolean {
        val isFavoriteSet = favoritesInfoLiveData.value.orEmpty().map { it.id }
        return isFavoriteSet.contains(movieId)
    }

    fun onFavoriteClick(movie: Movie) {
        if (isFavorite(movie.id)) {
            dataBase.favoriteDao().deleteById(movie.id)
        } else {
            dataBase.favoriteDao().insert(
                Favorite(
                    id = movie.id,
                    backPosterMainMovieImageUrl = movie.backPosterMainMovieImageUrl,
                    pg = movie.pg,
                    genres = movie.genres,
                    rating = movie.rating,
                    reviews = movie.reviews,
                    overview = movie.overview,
                    name = movie.name
                )
            )
        }
    }

    fun updateFavoriteState() {
        val pagingData = movieMutableLiveData.value ?: return

        val newPagingData = pagingData.map { it.copy(isFavorite = isFavorite(it.movie.id)) }

        movieMutableLiveData.postValue(newPagingData)
    }

    protected fun handlePagingSource(pagingSource: BaseMoviePagingSource) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10, maxSize = 40),
                pagingSourceFactory = {
                    pagingSource
                }
            ).flow.cachedIn(viewModelScope)
                .map {
                    it.map { movie ->
                        MovieUI(movie, isFavorite(movie.id))
                    }
                }.collectLatest { movieMutableLiveData.postValue(it) }
        }
    }
}
