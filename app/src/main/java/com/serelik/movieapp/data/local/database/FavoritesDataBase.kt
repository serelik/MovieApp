package com.serelik.movieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.serelik.movieapp.data.local.models.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoritesDataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        val DATABASE_NAME = "favorites.db"

        fun createDataBase(applicationContext: Context): FavoritesDataBase {
            return Room.databaseBuilder(
                applicationContext,
                FavoritesDataBase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
