package com.serelik.movieapp.ui.actorDetails


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Actor

class MoviesAdapter(
) : ListAdapter<Actor, MoviesViewHolder>(MoviesCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(inflater.inflate(R.layout.item_actor, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}