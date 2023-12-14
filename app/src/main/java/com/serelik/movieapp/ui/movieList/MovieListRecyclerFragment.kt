package com.serelik.movieapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.databinding.FragmentRecyclerBinding
import com.serelik.movieapp.ui.movieDetails.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListRecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private val viewModel: MovieListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentRecyclerBinding::bind)

    private val currentList: MovieListSpecific by lazy {
        arguments?.getString(listKey)?.let { MovieListSpecific.valueOf(it) }
            ?: MovieListSpecific.POPULAR
    }

    val movieAdapter = MovieAdapter() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MovieDetailsFragment.createFragment(it.id))
            .addToBackStack("Movie details")
            .commit()
    }

   /* private fun setState(state: LoadingResults<List<Movie>>) {
        when (state) {
            is LoadingResults.Error -> {
                setVisibility(isFailed = true)
            }

            LoadingResults.Loading -> {
                setVisibility(isLoading = true)
            }

            is LoadingResults.Success -> {
                setVisibility(isSucceed = true)

                movieAdapter.submitData(viewLifecycleOwner.lifecycle, state.dataInfo)
            }
        }
    }*/

    private fun setVisibility(
        isFailed: Boolean = false,
        isSucceed: Boolean = false,
        isLoading: Boolean = false
    ) {
        viewBinding.progressBarMovieList.isVisible = isLoading
        viewBinding.buttonTryAgain.isVisible = isFailed
        viewBinding.recyclerView.isVisible = isSucceed
    }

    private fun getInfo() {
        /*currentList.let { viewModel.getMovies(it) }*/
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

        /*viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)*/

        viewModel.getMovies(currentList).observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewBinding.recyclerView.adapter = movieAdapter
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
}
