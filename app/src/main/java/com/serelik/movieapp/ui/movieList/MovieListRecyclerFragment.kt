package com.serelik.movieapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.MovieListSpecific
import com.serelik.movieapp.databinding.FragmentRecyclerBinding
import com.serelik.movieapp.ui.movieDetails.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListRecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private val viewModel: MovieListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentRecyclerBinding::bind)

    private val gridLayoutManager by lazy {
        GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.RecyclerSpan),
            RecyclerView.VERTICAL,
            false
        ).apply {
            spanSizeLookup = this@MovieListRecyclerFragment.getSpanSizeLookup()
        }
    }

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

    val movieErrorLoadAdapter = MovieErrorLoadAdapter() {
        movieAdapter.retry()
    }

    /*  private fun setState(state: LoadingResults<List<Movie>>) {
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

    /*  private fun setVisibility(
          isFailed: Boolean = false,
          isSucceed: Boolean = false,
          isLoading: Boolean = false
      ) {
          viewBinding.progressBarMovieList.isVisible = isLoading
          viewBinding.buttonTryAgain.isVisible = isFailed
          viewBinding.recyclerView.isVisible = isSucceed
      }*/

    private fun getInfo() {
        viewModel.getMovies(currentList).observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.buttonTryAgain.setOnClickListener {
            getInfo()
        }

        getInfo()

        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest {
                viewBinding.progressBarMovieList.isVisible =
                    (it.refresh is LoadState.Loading)
                viewBinding.buttonTryAgain.isVisible =
                    (it.refresh is LoadState.Error)

                movieErrorLoadAdapter.loadState = it.append
            }
        }

        if (viewBinding.recyclerView.layoutManager == null) {
            // bug layout manager attach
            viewBinding.recyclerView.layoutManager = gridLayoutManager
        }

        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
        viewBinding.recyclerView.adapter =
            ConcatAdapter(config, movieAdapter, movieErrorLoadAdapter)

        (viewBinding.recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            getSpanSizeLookup()

        /*viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)*/
    }

    fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType =
                    viewBinding.recyclerView.adapter?.getItemViewType(position)
                //     Log.d("Viewtype check ", viewType.toString())
                return when (viewType) {
                    R.layout.item_movie -> 1
                    R.layout.item_error_load -> resources.getInteger(R.integer.RecyclerSpan)
                    else -> 1
                }
            }
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
}
