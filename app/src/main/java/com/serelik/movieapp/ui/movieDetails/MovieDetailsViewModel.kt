package com.serelik.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.ActorMapper
import com.serelik.movieapp.data.network.MovieDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val actorMapper: ActorMapper
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<LoadingResults<Pair<Movie, List<Actor>>>>()

    val movieInfoLiveData: LiveData<LoadingResults<Pair<Movie, List<Actor>>>> = mutableLiveData

    fun getMovieAndActorInfo(id: Int) {
        viewModelScope.launch {
            try {
                mutableLiveData.postValue(LoadingResults.Loading)
                val genresInfoId = movieApiService.getGenresId()
                val movieInfo = movieApiService.getMovie(id)
                val actorInfoResponse = movieApiService.getCredits(id)

                val actors = actorInfoResponse.cast.map {
                    actorMapper.parseActorResponse(it)
                }

                val genres = genresInfoId.genres.associateBy({ it.id }, { it.name })
                val movie = movieInfo.parseMovieResponse(genres)

                mutableLiveData.postValue(LoadingResults.Success(Pair(movie, actors)))
            } catch (e: Exception) {
                mutableLiveData.postValue(LoadingResults.Error(e))
            }

        }
    }

}