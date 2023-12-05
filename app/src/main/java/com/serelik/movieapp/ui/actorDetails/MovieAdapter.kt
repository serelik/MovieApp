package com.serelik.movieapp.ui.actorDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieByActor

class MovieAdapter() : ListAdapter<MovieByActor, MovieViewHolder>(MovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater.inflate(
                R.layout.item_short_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
