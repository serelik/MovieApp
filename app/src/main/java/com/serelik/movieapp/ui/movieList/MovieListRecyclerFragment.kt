package com.serelik.movieapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentRecyclerBinding
import com.serelik.movieapp.ui.movieDetails.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListRecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private val viewModel: MovieListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentRecyclerBinding::bind)

    val currentList by lazy { arguments?.getString(listKey) }

    val movieAdapter = MovieAdapter() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MovieDetailsFragment.createFragment(it.id))
            .addToBackStack("Movie details")
            .commit()
    }

    private fun setState(state: LoadingResults<List<Movie>>) {
        when (state) {
            is LoadingResults.Error -> {
                setVisibility(isTryButton = true)
            }

            LoadingResults.Loading -> {
                setVisibility(isLoading = true)
            }

            is LoadingResults.Success -> {
                setVisibility(isTextView = true)
                movieAdapter.submitList(state.dataInfo)
            }
        }
    }

    private fun setVisibility(
        isTryButton: Boolean = false,
        isTextView: Boolean = false,
        isLoading: Boolean = false
    ) {
        viewBinding.progressBarMovieList.isVisible = isLoading
        viewBinding.buttonTryAgain.isVisible = isTryButton
        viewBinding.recyclerView.isVisible = isTextView
    }

    private fun getInfo() {
        currentList?.let { viewModel.getMovieInfo(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.buttonTryAgain.setOnClickListener {
            getInfo()
        }

        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.recyclerView.adapter = movieAdapter
    }

    companion object {
        const val listKey = "current list"

        fun createFragment(listName: String): MovieListRecyclerFragment {
            val arg = Bundle()
            arg.putString(listKey, listName)
            val fragment = MovieListRecyclerFragment()
            fragment.arguments = arg
            return fragment
        }
    }
}
