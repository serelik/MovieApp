package com.serelik.movieapp.ui.movie

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.serelik.movieapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseMovieFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract val viewModel: BaseMovieListViewModel

    val movieAdapter by lazy {
        MovieAdapter(
            onMovieClickListener = { movie ->
                onMovieClick(movie.id)
            },
            onFavoriteClick = viewModel::onFavoriteClick,
            isFavoriteMovie = viewModel::isFavorite
        )
    }

    val movieErrorLoadAdapter = MovieErrorLoadAdapter() {
        movieAdapter.retry()
    }

    private fun getSpanSizeLookup(recyclerView: RecyclerView): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType =
                    recyclerView.adapter?.getItemViewType(position)
                return when (viewType) {
                    R.layout.item_movie -> 1
                    R.layout.item_error_load -> resources.getInteger(R.integer.RecyclerSpan)
                    else -> 1
                }
            }
        }
    }

    abstract fun onMovieClick(movieId: Int)

    fun setupRecycler(recyclerView: RecyclerView) {
        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
        recyclerView.adapter =
            ConcatAdapter(config, movieAdapter, movieErrorLoadAdapter)

        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            getSpanSizeLookup(recyclerView)
    }
}
