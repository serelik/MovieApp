package com.serelik.movieapp.ui.movie

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.databinding.ItemErrorLoadBinding

class MovieErrorLoadingViewHolder(view: View, val onRetryClick: () -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItemErrorLoadBinding::bind)

    fun bind(loadState: LoadState) {
        viewBinding.apply {
            progressBarMovieList.isVisible = loadState is LoadState.Loading
            buttonTryAgain.isVisible = loadState is LoadState.Error

            buttonTryAgain.setOnClickListener {
                onRetryClick.invoke()
            }
        }
    }
}
