package com.serelik.movieapp.ui.movie.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Actor

class ActorsAdapter(
    private val onMovieClickListener: (Actor) -> Unit
) : ListAdapter<Actor, ActorsViewHolder>(ActorsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ActorsViewHolder(
            inflater.inflate(R.layout.item_actor, parent, false),
            onMovieClickListener
        )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
