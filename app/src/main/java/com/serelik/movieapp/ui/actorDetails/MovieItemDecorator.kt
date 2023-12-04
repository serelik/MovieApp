package com.serelik.movieapp.ui.actorDetails

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.serelik.movieapp.R

class MovieItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = parent.adapter?.itemCount ?: return
        val position = parent.getChildAdapterPosition(view)

        val startPadding = if (position != 0)
            parent.resources.getDimensionPixelOffset(R.dimen.recyclerBetween) / 2
        else
            parent.resources.getDimensionPixelOffset(R.dimen.startAndEnd)


        val endPadding = if (position == itemCount - 1)
            parent.resources.getDimensionPixelOffset(R.dimen.startAndEnd)
        else
            parent.resources.getDimensionPixelOffset(R.dimen.recyclerBetween) / 2

        outRect.left = startPadding
        outRect.right = endPadding
    }


}