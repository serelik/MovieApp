package com.serelik.movieapp.ui.favoriteList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.movieapp.data.local.database.FavoritesDataBase
import com.serelik.movieapp.data.local.models.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val dataBase = FavoritesDataBase.createDataBase(context)

    private val favoritesMutableLiveData = MutableLiveData<List<Favorite>>()

    val favoritesInfoLiveData: LiveData<List<Favorite>> = favoritesMutableLiveData

    fun getFavoriteMovies() {
        viewModelScope.launch {
            dataBase.favoriteDao().getAll().collect { it ->
                favoritesMutableLiveData.postValue(it)
            }
        }
    }

    fun onFavoriteClick(movieId: Int) {
        dataBase.favoriteDao().deleteById(movieId)
    }
}
