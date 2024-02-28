package com.serelik.movieapp.ui.movie.movieSearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.ItemMovieBinding

class MovieViewHolder(
    view: View,
    private val onMovieClick: (Movie) -> Unit,
    private val isFavoriteMovie: (movieId: Int) -> Boolean,
    private val onFavoriteClick: (movie: Movie) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItemMovieBinding::bind)

    fun bind(movie: Movie) {
        viewBinding.apply {
            imageViewPoster.load(movie.backPosterMainMovieImageUrl)
            textViewGenres.text = movie.genres
            ratingBar.rating = movie.rating
            textViewPg.text = movie.pg
            textViewTitle.text = movie.name
            textViewReviews.text = "${movie.reviews} Reviews"

            imageViewPoster.setOnClickListener {
                onMovieClick.invoke(movie)
            }

            val isFavoriteMovie = isFavoriteMovie(movie.id)

            imageViewFavorite.isSelected = isFavoriteMovie

            imageViewFavorite.setOnClickListener {
                onFavoriteClick(movie)
                imageViewFavorite.isSelected = !imageViewFavorite.isSelected
            }
        }
    }
}
