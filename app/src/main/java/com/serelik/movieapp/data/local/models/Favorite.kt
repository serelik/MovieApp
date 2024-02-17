package com.serelik.movieapp.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int
)