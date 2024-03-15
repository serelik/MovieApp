package com.serelik.movieapp.ui.movie.details

import android.os.Bundle
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.Actor
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentMovieDetailsBinding
import com.serelik.movieapp.extensions.doOnApplyWindowInsets
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

        if (savedInstanceState == null) {
            movieId.let { viewModel.getMovieAndActorInfo(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        setupInsets()

        viewBinding.recyclerView.adapter = actorsAdapter

        viewModel.getFavoriteMovies()

        viewModel.favoritesInfoLiveData.observe(viewLifecycleOwner) {
            viewBinding.imageViewFavorite.isSelected = viewModel.isFavorite(movieId)
        }

        viewBinding.imageViewFavorite.setOnClickListener {
            viewModel.onFavoriteClick()
            viewBinding.imageViewFavorite.isSelected = !viewBinding.imageViewFavorite.isSelected
        }

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
            imageViewFavorite.isSelected = viewModel.isFavorite(movieId)
        }
    }

    private fun onActorClick(actorId: Int) {
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToActorDetailsFragment(
                actorId
            )
        )
    }

    private fun setupInsets() {
        viewBinding.root.doOnApplyWindowInsets { view, insets, rect ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewBinding.viewStatusBarBackground.updateLayoutParams {
                height = systemBarsInsets.top
            }
            WindowInsetsCompat.Builder()
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(),
                    Insets.of(
                        systemBarsInsets.left,
                        systemBarsInsets.top,
                        systemBarsInsets.right,
                        0
                    )
                ).build()
        }
    }
}
