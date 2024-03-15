package com.serelik.movieapp.ui.movie.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.ActorMapper
import com.serelik.movieapp.data.network.DetailsMovieMapper
import com.serelik.movieapp.data.network.MovieDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val actorMapper: ActorMapper,
    private val movieMapper: DetailsMovieMapper,
    @ApplicationContext context: Context
) : ViewModel() {

    private val dataBase = FavoritesDataBase.createDataBase(context)

    private val favoritesMutableLiveData = MutableLiveData<List<Favorite>>()
    val favoritesInfoLiveData: LiveData<List<Favorite>> = favoritesMutableLiveData

    private val mutableLiveData = MutableLiveData<LoadingResults<Pair<Movie, List<Actor>>>>()

    val movieInfoLiveData: LiveData<LoadingResults<Pair<Movie, List<Actor>>>> = mutableLiveData

    fun getFavoriteMovies() {
        viewModelScope.launch {
            dataBase.favoriteDao().getAll().collect { it ->
                favoritesMutableLiveData.postValue(it)
            }
        }
    }

    fun getMovie(): Movie? {
        val result = movieInfoLiveData.value as? LoadingResults.Success ?: return null

        return result.dataInfo.first
    }

    fun isFavorite(movieId: Int): Boolean {
        val isFavoriteSet = favoritesInfoLiveData.value.orEmpty().map { it.id }
        return isFavoriteSet.contains(movieId)
    }

    fun onFavoriteClick() {
        val movie = getMovie() ?: return
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

    fun getMovieAndActorInfo(id: Int) {
        viewModelScope.launch {
            try {
                mutableLiveData.postValue(LoadingResults.Loading)
                val movieInfo = movieApiService.getMovie(id)
                val actorInfoResponse = movieApiService.getCredits(id)

                val actors = actorInfoResponse.cast.map {
                    actorMapper.parseActorResponse(it)
                }

                val movie = movieMapper.parseDetailsMovieResponse(movieInfo)

                mutableLiveData.postValue(LoadingResults.Success(Pair(movie, actors)))
            } catch (e: Exception) {
                mutableLiveData.postValue(LoadingResults.Error(e))
            }
        }
    }
}
