package com.serelik.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.MovieDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieApiService: MovieDBApi
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<Pair<Movie, List<Actor>>>()

    val movieInfoLiveData: LiveData<Pair<Movie, List<Actor>>> = mutableLiveData

    fun getMovieAndActorInfo(id: Int) {
        viewModelScope.launch {
            val genresInfoId = movieApiService.getGenresId()
            val movieInfo = movieApiService.getMovie(id)
            val actorInfoResponse = movieApiService.getCredits(id)

            val actors = actorInfoResponse.cast.map {
                it.parseActorResponse()
            }

            val genres = genresInfoId.genres.associateBy({ it.id }, { it.name })
            val movie = movieInfo.parseMovieResponse(genres)

            mutableLiveData.postValue(Pair(movie, actors))

        }
    }

}