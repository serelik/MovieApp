package com.serelik.movieapp.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.fitOnTopInsets() {
    this.doOnApplyWindowInsets { view, insets, rect ->
        val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        this.updatePadding(
            top = rect.top + systemBarsInsets.top
        )
        insets.isConsumed
    }
}

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, InitialPadding) -> Unit) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

data class InitialPadding(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft,
    view.paddingTop,
    view.paddingRight,
    view.paddingBottom
)
