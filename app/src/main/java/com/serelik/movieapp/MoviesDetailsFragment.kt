package com.serelik.movieapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MoviesDetailsFragment: Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        val textViewBack = view.findViewById<TextView>(R.id.textViewBack)

        textViewBack.setOnClickListener {
            listener?.removeMovieDetails()
        }

        return view
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface ClickListener {
        fun removeMovieDetails()
    }
}