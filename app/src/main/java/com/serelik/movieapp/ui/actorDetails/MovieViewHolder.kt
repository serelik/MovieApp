package com.serelik.movieapp.ui.actorDetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieByActor
import com.serelik.movieapp.databinding.ItemShortMovieBinding

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val viewBinding = ItemShortMovieBinding.bind(view)

    fun bind(movie: MovieByActor) {
        viewBinding.imageViewPoster.load(movie.posterPath, R.drawable.actor_place_holder, true)
        viewBinding.textViewTitle.text = movie.title

    }
}