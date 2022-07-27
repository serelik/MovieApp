package com.serelik.movieapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class MovieListFragment : Fragment() {
   private var listener: ClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? ClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val cardViewFilm = view.findViewById<CardView>(R.id.cardViewFilmOne)
        cardViewFilm.setOnClickListener {
            listener?.openMovieDetails()
        }

        return view
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface ClickListener {
        fun openMovieDetails()
    }
}