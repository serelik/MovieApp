package com.serelik.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serelik.movieapp.ui.movieList.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(android.R.id.content, MovieListFragment())
                .commit()
        }
    }
}
