package com.serelik.movieapp.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Movie

class MovieAdapter(private val onMovieClickListener: (Movie) -> Unit) :
    ListAdapter<Movie, MovieViewHolder>(MovieCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            ),
            onMovieClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
