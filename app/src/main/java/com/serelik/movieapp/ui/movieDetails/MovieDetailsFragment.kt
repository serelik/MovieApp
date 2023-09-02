package com.serelik.movieapp.ui.movieDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.actor.ActorsAdapter
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    val movieInfo by lazy { arguments?.getInt(movieKey) }

    private val viewBinding by viewBinding(FragmentMovieDetailsBinding::bind)

    val supportFragmentManager by lazy { requireActivity().supportFragmentManager }

    val actorsAdapter = ActorsAdapter()

    private fun setState(movieInfo: Pair<Movie, List<Actor>>) {
        actorsAdapter.submitList(movieInfo.second)
        setInfo(movieInfo.first)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("movieInfo checka", movieInfo.toString())
        movieInfo?.let { viewModel.getMovieAndActorInfo(it) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.recyclerView.adapter = actorsAdapter

        viewBinding.textViewButtonBack.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

    fun setInfo(movieInfo: Movie) {
        viewBinding.apply {
            textViewPG.text = movieInfo.pg
            textViewMovieName.text = movieInfo.name
            textViewGenres.text = movieInfo.genres
            ratingBar.rating = movieInfo.rating
            textViewReviews.text = "${movieInfo.reviews} REVIEWS"
            textViewFilmOverview.text = movieInfo.overview
            recyclerView.adapter = actorsAdapter
            imageViewBackPoster.load(movieInfo.backdropMovieDetailsImageUrl)

        }
    }

    companion object {
        const val movieKey = "Movie_info"

        fun createFragment(id: Int): MovieDetailsFragment {
            val arg = Bundle()
            arg.putInt(movieKey, id)
            val fragment = MovieDetailsFragment()
            fragment.arguments = arg
            return fragment
        }
    }

}