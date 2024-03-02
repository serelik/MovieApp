package com.serelik.movieapp.ui.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.data.local.models.MovieUI
import com.serelik.movieapp.databinding.ItemMovieBinding

class MovieViewHolder(
    view: View,
    private val onMovieClick: (Movie) -> Unit,
    private val onFavoriteClick: (movie: Movie) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItemMovieBinding::bind)

    fun bind(movieUI: MovieUI) {
        viewBinding.apply {
            imageViewPoster.load(movieUI.movie.backPosterMainMovieImageUrl)
            textViewGenres.text = movieUI.movie.genres
            ratingBar.rating = movieUI.movie.rating
            textViewPg.text = movieUI.movie.pg
            textViewTitle.text = movieUI.movie.name
            textViewReviews.text = "${movieUI.movie.reviews} Reviews"

            imageViewPoster.setOnClickListener {
                onMovieClick.invoke(movieUI.movie)
            }

            imageViewFavorite.isSelected = movieUI.isFavorite

            imageViewFavorite.setOnClickListener {
                onFavoriteClick(movieUI.movie)
                imageViewFavorite.isSelected = !imageViewFavorite.isSelected
            }
        }
    }
}
