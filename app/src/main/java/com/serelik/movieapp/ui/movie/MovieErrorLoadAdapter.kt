package com.serelik.movieapp.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.serelik.movieapp.R

class MovieErrorLoadAdapter(private val onRetryClickListener: () -> Unit) :
    LoadStateAdapter<MovieErrorLoadingViewHolder>() {
    override fun onBindViewHolder(holder: MovieErrorLoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return R.layout.item_error_load
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieErrorLoadingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieErrorLoadingViewHolder(
            inflater.inflate(
                R.layout.item_error_load,
                parent,
                false
            ),
            onRetryClickListener
        )
    }
}
