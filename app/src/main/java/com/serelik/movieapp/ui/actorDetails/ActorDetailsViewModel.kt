package com.serelik.movieapp.ui.actorDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.ActorDetails
import com.serelik.movieapp.data.local.models.MovieByActor
import com.serelik.movieapp.data.network.ActorDetailsMapper
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MoviesByActorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val moviesByActorMapper: MoviesByActorMapper,
    private val actorDetailsMapper: ActorDetailsMapper
) : ViewModel() {

    private val mutableLiveData =
        MutableLiveData<LoadingResults<Pair<ActorDetails, List<MovieByActor>>>>()

    val movieInfoLiveData: LiveData<LoadingResults<Pair<ActorDetails, List<MovieByActor>>>> =
        mutableLiveData

    fun getMovieAndActorInfo(id: Int) {
        viewModelScope.launch {
            try {
                mutableLiveData.postValue(LoadingResults.Loading)
                val moviesResponse = movieApiService.getMoviesByActor(id)
                val actorInfoResponse = movieApiService.getActor(id)

                val actor = actorDetailsMapper.parseActorDetailsResponse(actorInfoResponse)

                val movies =
                    moviesResponse.movies.map { moviesByActorMapper.parseMovieByActorResponse(it) }

                mutableLiveData.postValue(LoadingResults.Success(Pair(actor, movies)))
            } catch (e: Exception) {
                mutableLiveData.postValue(LoadingResults.Error(e))
            }
        }
    }
}
