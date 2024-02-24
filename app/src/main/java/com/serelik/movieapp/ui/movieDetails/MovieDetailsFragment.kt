package com.serelik.movieapp.ui.movieDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.actor.ActorsAdapter
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewBinding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val movieId by lazy { args.movieId }

    private val actorsAdapter = ActorsAdapter {
        onActorClick(it.id)
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

        movieId.let { viewModel.getMovieAndActorInfo(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.recyclerView.adapter = actorsAdapter

        viewBinding.textViewButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.buttonTryAgain.setOnClickListener {
            movieId.let { viewModel.getMovieAndActorInfo(it) }
        }
    }

    private fun setInfo(movieInfo: Movie) {
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

    private fun onActorClick(actorId: Int) {
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToActorDetailsFragment(
                actorId
            )
        )
    }
}
