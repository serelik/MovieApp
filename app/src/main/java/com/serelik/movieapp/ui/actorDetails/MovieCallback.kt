package com.serelik.movieapp.ui.actorDetails

import androidx.recyclerview.widget.DiffUtil
import com.serelik.movieapp.data.local.models.MovieByActor

class MovieCallback : DiffUtil.ItemCallback<MovieByActor>() {
    override fun areItemsTheSame(oldItem: MovieByActor, newItem: MovieByActor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieByActor, newItem: MovieByActor): Boolean {
        return oldItem == newItem
    }


}