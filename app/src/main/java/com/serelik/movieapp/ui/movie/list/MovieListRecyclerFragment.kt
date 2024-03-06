package com.serelik.movieapp.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.databinding.FragmentRecyclerBinding
import com.serelik.movieapp.ui.movie.BaseMovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListRecyclerFragment : BaseMovieFragment(R.layout.fragment_recycler) {

    override val viewModel: MovieListMovieListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentRecyclerBinding::bind)

    private val currentList: MovieListSpecific by lazy {
        arguments?.getString(listKey)?.let { MovieListSpecific.valueOf(it) }
            ?: MovieListSpecific.POPULAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMovies(currentList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch { viewModel.getFavoriteMovies() }
        super.onViewCreated(view, savedInstanceState)

        viewBinding.buttonTryAgain.setOnClickListener {
            viewModel.getMovies(currentList)
        }

        viewBinding.recyclerView.itemAnimator = null

        bindMovieList()

        lifecycleScope.launch {
            bindState()
        }

        setupRecycler(viewBinding.recyclerView)
    }

    private suspend fun bindState() {
        movieAdapter.loadStateFlow.collectLatest {
            viewBinding.progressBarMovieList.isVisible =
                (it.refresh is LoadState.Loading)
            viewBinding.buttonTryAgain.isVisible =
                (it.refresh is LoadState.Error)

            movieErrorLoadAdapter.loadState = it.append
        }
    }

    companion object {
        const val listKey = "current list"

        fun createFragment(movieListType: MovieListSpecific): MovieListRecyclerFragment {
            val arg = Bundle()
            arg.putString(listKey, movieListType.name)
            val fragment = MovieListRecyclerFragment()
            fragment.arguments = arg
            return fragment
        }
    }

    override fun onMovieClick(movieId: Int) {
        val controller = findNavController()
        controller.navigate(
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }
}
