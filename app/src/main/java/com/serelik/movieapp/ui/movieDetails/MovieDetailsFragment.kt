package com.serelik.movieapp.ui.movieDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.actor.ActorsAdapter
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentMovieDetailsBinding
import com.serelik.movieapp.ui.actorDetails.ActorDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    val movieInfo by lazy { arguments?.getInt(movieKey) }

    private val viewBinding by viewBinding(FragmentMovieDetailsBinding::bind)

    val supportFragmentManager by lazy { requireActivity().supportFragmentManager }

    val actorsAdapter = ActorsAdapter {
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ActorDetailsFragment.createFragment(it.id))
            .addToBackStack("Actor details")
            .commit()
    }

    private fun setState(state: LoadingResults<Pair<Movie, List<Actor>>>) {
        when (state) {
            is LoadingResults.Error -> {
                setVisibility(isFailed = true)
            }

            LoadingResults.Loading -> {
                setVisibility(isLoading = true)
            }

            is LoadingResults.Success -> {
                actorsAdapter.submitList(state.dataInfo.second)
                setVisibility(isSucceed = true)
                setInfo(state.dataInfo.first)
            }
        }
    }

    private fun setVisibility(
        isFailed: Boolean = false,
        isSucceed: Boolean = false,
        isLoading: Boolean = false
    ) {
        viewBinding.progressBarMovieList.isVisible = isLoading
        viewBinding.buttonTryAgain.isVisible = isFailed
        viewBinding.recyclerView.isVisible = isSucceed
        viewBinding.textViewStoryline.isVisible = isSucceed
        viewBinding.ratingBar.isVisible = isSucceed
        viewBinding.textViewCast.isVisible = isSucceed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieInfo?.let { viewModel.getMovieAndActorInfo(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.recyclerView.adapter = actorsAdapter

        viewBinding.textViewButtonBack.setOnClickListener {
            supportFragmentManager.popBackStack()
        }

        viewBinding.buttonTryAgain.setOnClickListener {
            movieInfo?.let { viewModel.getMovieAndActorInfo(it) }
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
