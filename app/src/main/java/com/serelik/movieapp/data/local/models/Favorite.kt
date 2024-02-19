package com.serelik.movieapp.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    val backPosterMainMovieImageUrl: String?,
    val pg: String,
    val genres: String,
    val rating: Float,
    val reviews: Int,
    val overview: String,
    val name: String
)
