package com.serelik.movieapp.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieUI

class MovieAdapter(
    private val onMovieClickListener: (Movie) -> Unit,
    private val onFavoriteClick: (movie: Movie) -> Unit
) :
    PagingDataAdapter<MovieUI, MovieViewHolder>(MovieCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            ),
            onMovieClickListener,
            onFavoriteClick
        )
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
