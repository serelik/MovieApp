package com.serelik.movieapp.ui.actorDetails

import android.util.Log
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
import kotlinx.coroutines.async
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
        val moviesResponseAsync = viewModelScope.async { movieApiService.getMoviesByActor(id) }
        val actorInfoResponseAsync = viewModelScope.async { movieApiService.getActor(id) }

        viewModelScope.launch {
            try {
                mutableLiveData.postValue(LoadingResults.Loading)

                val moviesResponse = moviesResponseAsync.await()
                val actorInfoResponse = actorInfoResponseAsync.await()

                val actor = actorDetailsMapper.parseActorDetailsResponse(actorInfoResponse)

                val movies =
                    moviesResponse.movies.map { moviesByActorMapper.parseMovieByActorResponse(it) }

                mutableLiveData.postValue(LoadingResults.Success(Pair(actor, movies)))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(this::class.simpleName, e.message.toString())
                mutableLiveData.postValue(LoadingResults.Error(e))
            }
        }
    }
}
