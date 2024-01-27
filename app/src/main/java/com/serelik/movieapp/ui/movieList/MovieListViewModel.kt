package com.serelik.movieapp.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.serelik.movieapp.data.local.models.GenresStorage
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.data.network.MovieDBApi
import com.serelik.movieapp.data.network.MovieMapper
import com.serelik.movieapp.data.network.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieApiService: MovieDBApi,
    private val movieMapper: MovieMapper,
    private val genresStorage: GenresStorage
) : ViewModel() {

    /*private val mutableLiveData = MutableLiveData<LoadingResults<List<Movie>>>() //PagingLi

    val movieInfoLiveData: LiveData<LoadingResults<List<Movie>>> = mutableLiveData*/

    /* fun getMovieInfo(specific: String) {
         viewModelScope.launch {
             try {
                 mutableLiveData.postValue(LoadingResults.Loading)

                 *//* val genresInfoId = movieApiService.getGenresId()
                 val movieInfo = movieApiService.getMoviesList(specific)*//*

                *//*  val genres = genresInfoId.genres.associateBy({ it.id }, { it.name })
                  val movies = movieInfo.movies.map { movieMapper.parseMovieResponse(genres, it) }*//*

                mutableLiveData.postValue(LoadingResults.Success(movies))
            } catch (e: Exception) {
                mutableLiveData.postValue(LoadingResults.Error(e))
            }
        }
    }*/

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
