package com.serelik.movieapp.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<LoadingResults<List<Movie>>>()

    val movieInfoLiveData: LiveData<LoadingResults<List<Movie>>> = mutableLiveData

    fun getMovieInfo(specific: String) {
        viewModelScope.launch {
            try {
                mutableLiveData.postValue(LoadingResults.Loading)
                val genresInfoId = movieApiService.getGenresId()
                val movieInfo = movieApiService.getMoviesList(specific)

                val genres = genresInfoId.genres.associateBy({ it.id }, { it.name })
                val movies = movieInfo.movies.map { movieMapper.parseMovieResponse(genres, it) }

                mutableLiveData.postValue(LoadingResults.Success(movies))
            } catch (e: Exception) {
                mutableLiveData.postValue(LoadingResults.Error(e))
            }
        }
    }
}
