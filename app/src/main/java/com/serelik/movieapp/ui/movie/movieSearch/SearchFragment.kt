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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.databinding.FragmentSearchBinding
import com.serelik.movieapp.extensions.doOnApplyWindowInsets
import com.serelik.movieapp.ui.movie.BaseMovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseMovieFragment(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentSearchBinding::bind)

    private fun bindMovieList() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
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

        setupRecycler(viewBinding.recyclerView)
    }

    private suspend fun bindState() {
        movieAdapter.loadStateFlow.collectLatest {
            viewBinding.progressBarMovieSearchList.isVisible =
                (it.refresh is LoadState.Loading)
            viewBinding.buttonTryAgain.isVisible =
                (it.refresh is LoadState.Error)

            if (it.append.endOfPaginationReached) {
                viewBinding.textViewNoMovie.isVisible = movieAdapter.itemCount < 1
            }

            movieErrorLoadAdapter.loadState = it.append
        }
    }

    override fun onMovieClick(movieId: Int) {
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
