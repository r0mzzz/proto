package com.example.moviedetails.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.core.base.BaseFragment
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.moviedetails.databinding.FragmentMovieDetailsBinding
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import com.example.moviedetails.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<MovieDetailsPageState, MovieDetailsPageEffect, FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private val args by navArgs<MovieDetailsFragmentArgs>()


    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate

    override fun getViewModelClass(): Class<MovieDetailsViewModel> =
        MovieDetailsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.setTitle("MovieDetails")
        }
        viewmodel.getMovieDetail(args.movieId)
    }

    private fun updateMovieDetails(response: MovieDetailsModel){
        binding.movieTitle.text = response.nameEn ?: response.nameRu
    }

    override fun observeState(state: MovieDetailsPageState) {
        when (state) {
            is MovieDetailsPageState.GetMovieDetailSuccess -> {
                updateMovieDetails(state.response)
            }
        }
    }

}