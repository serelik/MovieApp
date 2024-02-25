package com.serelik.movieapp.ui.movieSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Movie

class SearchAdapter(
    private val onMovieClickListener: (Movie) -> Unit,
    private val isFavoriteMovie: (movieId: Int) -> Boolean,
    private val onFavoriteClick: (movie: Movie) -> Unit
) :
    PagingDataAdapter<Movie, SearchViewHolder>(SearchCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            ),
            onMovieClickListener,
            isFavoriteMovie,
            onFavoriteClick
        )
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
