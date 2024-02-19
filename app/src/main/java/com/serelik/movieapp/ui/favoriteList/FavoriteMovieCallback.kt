package com.serelik.movieapp.ui.favoriteList

import androidx.recyclerview.widget.DiffUtil
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.Movie

class FavoriteMovieCallback : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }
}
