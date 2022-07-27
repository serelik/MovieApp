package com.serelik.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MovieListFragment.ClickListener, MoviesDetailsFragment.ClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainContainerList, MovieListFragment())
                .commit()
        }
    }

    override fun openMovieDetails() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainerList, MoviesDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun removeMovieDetails() {
        supportFragmentManager.popBackStack()
    }
}