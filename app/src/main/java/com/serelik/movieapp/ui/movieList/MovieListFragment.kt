package com.serelik.movieapp.ui.movieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serelik.movieapp.R
import com.serelik.movieapp.data.local.models.Movie
import com.serelik.movieapp.databinding.FragmentMovieListBinding
import com.serelik.movieapp.ui.movieDetails.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewModel: MovieListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentMovieListBinding::bind)

    val movieAdapter = MovieAdapter() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MovieDetailsFragment.createFragment(it.id))
            .addToBackStack("Movie details")
            .commit()
    }

    private fun setState(movies: List<Movie>) {
        movieAdapter.submitList(movies)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMovieInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.movieInfoLiveData.observe(viewLifecycleOwner, ::setState)

        viewBinding.recyclerView.adapter = movieAdapter
    }

}