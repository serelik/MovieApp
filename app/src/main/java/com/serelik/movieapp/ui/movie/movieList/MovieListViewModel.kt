package com.serelik.movieapp.ui.movie.movieList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage,
    @ApplicationContext context: Context
) : ViewModel() {

    private val dataBase = FavoritesDataBase.createDataBase(context)

    private val favoritesMutableLiveData = MutableLiveData<List<Favorite>>()

    val favoritesInfoLiveData: LiveData<List<Favorite>> = favoritesMutableLiveData

    init {
        viewModelScope.launch {
            dataBase.favoriteDao().getAll().collect { it ->
                favoritesMutableLiveData.postValue(it)
            }
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
