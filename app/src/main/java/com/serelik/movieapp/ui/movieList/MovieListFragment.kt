package com.serelik.movieapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.databinding.FragmentMovieListBinding
import com.serelik.movieapp.extensions.fitOnTopInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewBinding by viewBinding(FragmentMovieListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.fitOnTopInsets()

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        val movieListTypes = MovieListSpecific.values()

        viewBinding.viewPager.adapter = adapter

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = getString(movieListTypes[position].titleRes)
        }.attach()
    }
}
