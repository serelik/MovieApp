package com.serelik.movieapp.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.network.MovieDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieApiService: MovieDBApi
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<Movie>>()

    val movieInfoLiveData: LiveData<List<Movie>> = mutableLiveData

    fun getMovieInfo() {
        viewModelScope.launch {
            val genresInfoId = movieApiService.getGenresId()
            val movieInfo = movieApiService.getPopularMovie()

            val genres = genresInfoId.genres.associateBy({ it.id }, { it.name })
            val movies = movieInfo.movies.map { it.parseMovieResponse(genres) }

            mutableLiveData.postValue(movies)

        }
    }

}