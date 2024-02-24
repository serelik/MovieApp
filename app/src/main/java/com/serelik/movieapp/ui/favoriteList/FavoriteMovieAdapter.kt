package com.serelik.movieapp.ui.favoriteList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Favorite

class FavoriteMovieAdapter(
    private val onMovieClickListener: (Favorite) -> Unit,
    private val onFavoriteClick: (movieId: Int) -> Unit
) :
    ListAdapter<Favorite, FavoriteMovieViewHolder>(FavoriteMovieCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return FavoriteMovieViewHolder(
            inflater.inflate(
                R.layout.item_favorite_movie,
                parent,
                false
            ),
            onMovieClickListener,
            onFavoriteClick
        )
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_favorite_movie
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
