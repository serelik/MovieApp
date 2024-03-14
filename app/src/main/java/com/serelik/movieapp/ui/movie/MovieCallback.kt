package com.serelik.movieapp.ui.movie

import androidx.recyclerview.widget.DiffUtil
import com.serelik.movieapp.data.local.models.MovieUI

class MovieCallback : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem == newItem
    }
}
