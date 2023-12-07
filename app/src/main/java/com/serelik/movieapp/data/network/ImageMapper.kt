package com.serelik.movieapp.data.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageMapper @Inject constructor() {

    fun createImageUrl(picture: String?): String? {
        return picture?.let { "$baseImageUrl$picture" }
    }

    companion object {
        private const val baseImageUrl = "https://image.tmdb.org/t/p/original"
    }
}
