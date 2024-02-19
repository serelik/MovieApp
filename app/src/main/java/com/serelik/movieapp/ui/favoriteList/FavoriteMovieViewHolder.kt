package com.serelik.movieapp.ui.favoriteList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.data.local.models.Favorite
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.ItemFavoriteMovieBinding
import com.serelik.movieapp.databinding.ItemMovieBinding

class FavoriteMovieViewHolder(
    view: View, private val onMovieClick: (Favorite) -> Unit,
    private val onFavoriteClick: (movieId: Int) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItemFavoriteMovieBinding::bind)

    fun bind(movie: Favorite) {
        viewBinding.apply {
            imageViewPoster.load(movie.backPosterMainMovieImageUrl)
            textViewGenres.text = movie.genres
            ratingBar.rating = movie.rating
            textViewPg.text = movie.pg
            textviewMovieName.text = movie.name
            textViewReviews.text = "${movie.reviews} Reviews"
            textviewMovieDescription.text = movie.overview

            imageViewPoster.setOnClickListener {
                onMovieClick.invoke(movie)
            }

            imageViewFavorite.isSelected = true

            imageViewFavorite.setOnClickListener {
                onFavoriteClick(movie.id)
                imageViewFavorite.isSelected = !imageViewFavorite.isSelected
            }
        }
    }
}
