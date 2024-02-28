package com.serelik.movieapp.ui.movie.movieSearch

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.databinding.FragmentSearchBinding
import com.serelik.movieapp.extensions.doOnApplyWindowInsets
import com.serelik.movieapp.ui.movie.BaseMovieFragment
import com.serelik.movieapp.ui.movie.MovieAdapter
import com.serelik.movieapp.ui.movie.MovieErrorLoadAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseMovieFragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentSearchBinding::bind)

    private val searchAdapter by lazy {
        MovieAdapter(
            onMovieClickListener = { movie ->

                onMovieClick(movie.id)
            },
            onFavoriteClick = viewModel::onFavoriteClick,
            isFavoriteMovie = viewModel::isFavorite
        )
    }

    private val searchErrorLoadAdapter = MovieErrorLoadAdapter() {
        searchAdapter.retry()
    }

    private fun bindMovieList() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun onInputTextChange() {
        viewBinding.textInputEditTextSearch.doOnTextChanged { text, start, before, count ->
            viewModel.onQueryChange(text?.toString()?.trim().orEmpty())

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInsets()

        bindMovieList()

        onInputTextChange()

        lifecycleScope.launch {
            bindState()
        }

        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
        viewBinding.recyclerView.adapter =
            ConcatAdapter(config, searchAdapter, searchErrorLoadAdapter)

        (viewBinding.recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            getSpanSizeLookup(viewBinding.recyclerView)
    }

    private suspend fun bindState() {
        searchAdapter.loadStateFlow.collectLatest {
            viewBinding.progressBarMovieSearchList.isVisible =
                (it.refresh is LoadState.Loading)
            viewBinding.buttonTryAgain.isVisible =
                (it.refresh is LoadState.Error)

            if (it.append.endOfPaginationReached) {
                viewBinding.textViewNoMovie.isVisible = searchAdapter.itemCount < 1
            }

            searchErrorLoadAdapter.loadState = it.append
        }
    }

    private fun onMovieClick(movieId: Int) {
        val controller = findNavController()
        controller.navigate(
            SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }

    private fun setupInsets() {
        viewBinding.root.doOnApplyWindowInsets { view, insets, rect ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            viewBinding.root.updatePadding(
                top = rect.top + systemBarsInsets.top
            )

            insets.isConsumed
        }
    }
}
