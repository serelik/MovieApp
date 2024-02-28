package com.serelik.movieapp.ui.movie.details

import androidx.recyclerview.widget.DiffUtil
import com.serelik.movieapp.data.local.models.Actor

class ActorsCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}
