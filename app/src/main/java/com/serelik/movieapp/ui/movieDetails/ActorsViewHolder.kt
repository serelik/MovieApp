package com.example.movieappst.ui.actor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.databinding.ItemActorBinding

class ActorsViewHolder(view: View, val onActorClick: (Actor) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val viewBinding = ItemActorBinding.bind(view)

    fun bind(actor: Actor) {
        viewBinding.imageViewActor.load(actor.picture, R.drawable.actor_place_holder, true)
        viewBinding.textViewActorName.text = actor.name

        viewBinding.imageViewActor.setOnClickListener {
            onActorClick.invoke(actor)
        }
    }
}
