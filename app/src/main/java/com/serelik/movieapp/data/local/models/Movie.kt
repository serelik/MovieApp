package com.serelik.movieapp.data.local.models


data class Movie(
    val id: Int,
    val backPosterMainMovieImageUrl: String?,
    val backdropMovieDetailsImageUrl: String?,
    val pg: String,
    val genres: String,
    val rating: Float,
    val reviews: Int,
    val overview: String,
    val name: String,
)