package com.serelik.movieapp.ui.movieSearch

import androidx.recyclerview.widget.DiffUtil
import com.serelik.movieapp.data.local.models.Movie

class SearchCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
