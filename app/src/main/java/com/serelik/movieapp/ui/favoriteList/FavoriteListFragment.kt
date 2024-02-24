package com.serelik.movieapp.ui.favoriteList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.databinding.FragmentFavoriteListBinding
import com.serelik.movieapp.ui.movieList.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment(R.layout.fragment_favorite_list) {

    private val viewModel: FavoriteListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentFavoriteListBinding::bind)

    private val favoriteMovieAdapter by lazy {
        FavoriteMovieAdapter(
            onMovieClickListener = { movie ->
                onMovieClick(movie.id)
            },
            onFavoriteClick = viewModel::onFavoriteClick
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

    private fun onMovieClick(movieId: Int) {
        val controller = findNavController()
        controller.navigate(
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }
}
