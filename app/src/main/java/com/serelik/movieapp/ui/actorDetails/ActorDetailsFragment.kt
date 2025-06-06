package com.serelik.movieapp.ui.actorDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieappst.ui.extensions.load
import com.serelik.movieapp.R
import com.serelik.movieapp.data.LoadingResults
import com.serelik.movieapp.data.local.models.ActorDetails
import com.serelik.movieapp.data.local.models.MovieByActor
import com.serelik.movieapp.databinding.FragmentActorBinding
import com.serelik.movieapp.extensions.fitOnTopInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : Fragment(R.layout.fragment_actor) {

    private val viewModel: ActorDetailsViewModel by viewModels()

    private val args: ActorDetailsFragmentArgs by navArgs()

    private val actorId by lazy { args.actorId }

    private val viewBinding by viewBinding(FragmentActorBinding::bind)

    private val moviesAdapter = MovieAdapter()

    private fun setState(state: LoadingResults<Pair<ActorDetails, List<MovieByActor>>>) {
        when (state) {
            is LoadingResults.Error -> {
                setVisibility(isFailed = true)
            }

            LoadingResults.Loading -> {
                setVisibility(isLoading = true)
            }

            is LoadingResults.Success -> {
                moviesAdapter.submitList(state.dataInfo.second)
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
        viewBinding.textViewBiographyTitle.isVisible = isSucceed
        viewBinding.textViewFilmography.isVisible = isSucceed
        viewBinding.textViewSeeAll.isVisible = isSucceed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            actorId.let { viewModel.getMovieAndActorInfo(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.root.fitOnTopInsets()

        viewBinding.recyclerView.adapter = moviesAdapter
        viewBinding.recyclerView.addItemDecoration(MovieItemDecorator())

        viewBinding.buttonTryAgain.setOnClickListener {
            actorId.let { viewModel.getMovieAndActorInfo(it) }
        }

        viewBinding.textViewButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setInfo(actorInfo: ActorDetails) {
        viewBinding.apply {
            textViewActorName.text = actorInfo.actorName
            textViewBiography.text = actorInfo.biography
            textViewBirthday.text = actorInfo.birthday
            textViewBirthPlace.text = actorInfo.birthPlace
            imageViewActor.load(
                actorInfo.profilePicture,
                placeHolder = R.drawable.actor_place_holder
            )
            textViewKnownFor.text = actorInfo.knownFor
        }
    }
}
