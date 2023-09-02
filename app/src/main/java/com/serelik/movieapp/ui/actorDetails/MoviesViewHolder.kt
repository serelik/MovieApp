package com.serelik.movieapp.ui.actorDetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.databinding.ItemActorBinding

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val viewBinding = ItemActorBinding.bind(view)

    fun bind(actors: Actor) {
        viewBinding.imageViewActor.load(actors.picture, R.drawable.actor_place_holder, true)
        viewBinding.textViewActorName.text = actors.name
    }
}