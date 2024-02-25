package com.serelik.movieapp.ui.movieSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.serelik.movieapp.R

class SearchErrorLoadAdapter(private val onRetryClickListener: () -> Unit) :
    LoadStateAdapter<SearchErrorLoadingViewHolder>() {
    override fun onBindViewHolder(holder: SearchErrorLoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return R.layout.item_error_load
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SearchErrorLoadingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return SearchErrorLoadingViewHolder(
            inflater.inflate(
                R.layout.item_error_load,
                parent,
                false
            ),
            onRetryClickListener
        )
    }
}
