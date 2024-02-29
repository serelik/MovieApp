package com.serelik.movieapp.ui.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MovieSearchPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel (
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : ViewModel() {
    private val dataBase = FavoritesDataBase.createDataBase(context)

    private val favoritesMutableLiveData = MutableLiveData<List<Favorite>>()
    private val favoritesInfoLiveData: LiveData<List<Favorite>> = favoritesMutableLiveData

    suspend fun getFavoriteMovies() {
        dataBase.favoriteDao().getAll().collect { it ->
            favoritesMutableLiveData.postValue(it)
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
}