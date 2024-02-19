package com.serelik.movieapp.ui.favoriteList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.databinding.FragmentFavoriteListBinding
import com.serelik.movieapp.databinding.FragmentRecyclerBinding
import com.serelik.movieapp.ui.movieDetails.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment(R.layout.fragment_favorite_list) {

    private val viewModel: FavoriteListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentFavoriteListBinding::bind)

    val favoriteMovieAdapter by lazy {
        FavoriteMovieAdapter(
            onMovieClickListener = { movie ->
                val supportFragmentManager = requireActivity().supportFragmentManager
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, MovieDetailsFragment.createFragment(movie.id))
                    .addToBackStack("Movie details")
                    .commit()
            }, onFavoriteClick = viewModel::onFavoriteClick
        )
    }

    private fun bindMovieList() {
        viewBinding.recyclerView.adapter = favoriteMovieAdapter
        viewModel.favoritesInfoLiveData.observe(viewLifecycleOwner) {
            favoriteMovieAdapter.submitList(it)

            viewBinding.textviewNoMovies.isVisible = it.isEmpty()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteMovies()

        bindMovieList()
    }
}
