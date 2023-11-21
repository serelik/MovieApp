package com.serelik.movieapp.data.local.models

import androidx.annotation.StringRes
import com.serelik.movieapp.R

enum class MovieListSpecific(
    val remotePath: String,
    @StringRes val titleRes: Int,
) {
    POPULAR("popular", R.string.tab_popular),
    NOW_PLAYING("now_playing", R.string.tab_now_playing),
    UPCOMING("upcoming", R.string.tab_upcoming)
}