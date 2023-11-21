package com.serelik.movieapp.ui.movieList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.serelik.movieapp.data.local.models.MovieListSpecific

class ViewPagerAdapter(fragManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragManager, lifecycle) {

    val moviesList = MovieListSpecific.values()

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun createFragment(position: Int): Fragment {
        val type = moviesList[position]
        return MovieListRecyclerFragment.createFragment(type.remotePath)

        }
    }