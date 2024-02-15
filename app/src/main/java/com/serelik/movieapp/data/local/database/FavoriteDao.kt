package com.serelik.movieapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.serelik.movieapp.data.local.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<Favorite>>

    @Insert
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :movieId")
    fun deleteById(movieId: Int)

}