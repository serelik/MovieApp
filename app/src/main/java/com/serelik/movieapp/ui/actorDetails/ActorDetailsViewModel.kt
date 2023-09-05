package com.serelik.movieapp.ui.actorDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.local.models.ActorDetails
import com.serelik.movieapp.data.local.models.MovieByActor
import com.serelik.movieapp.data.network.MovieDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val movieApiService: MovieDBApi
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<Pair<ActorDetails, List<MovieByActor>>>()

    val movieInfoLiveData: LiveData<Pair<ActorDetails, List<MovieByActor>>> = mutableLiveData

    fun getMovieAndActorInfo(id: Int) {
        viewModelScope.launch {
            val moviesResponse = movieApiService.getMoviesByActor(id)
            val actorInfoResponse = movieApiService.getActor(id)

            val actor = actorInfoResponse.parseActorResponse()

            val movies = moviesResponse.movies.map { it.parseMovieByActorResponse() }

            mutableLiveData.postValue(Pair(actor, movies))

        }
    }

}