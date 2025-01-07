package com.example.moviedetails.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.domain.decorations.GridItemDecoration
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.domain.entity.moviedetails.TrailerItems
import com.example.moviedetails.adapter.MovieReviewListAdapter
import com.example.moviedetails.databinding.MovieReviewBinding
import com.example.moviedetails.databinding.SimilarLayoutBinding
import com.example.moviedetails.effect.MovieReviewPageEffect
import com.example.moviedetails.effect.SimilarMoviesPageEffect
import com.example.moviedetails.state.MovieReviewPageState
import com.example.moviedetails.state.SimilarMoviesPageState
import com.example.moviedetails.viewmodel.MovieReviewPageViewModel
import com.example.moviedetails.viewmodel.SimilarMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieReviewFragment() :
    BaseFragment<MovieReviewPageState, MovieReviewPageEffect, MovieReviewBinding, MovieReviewPageViewModel>() {

    private var movieId = ""
    private lateinit var reviewsAdapter: MovieReviewListAdapter
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> MovieReviewBinding
        get() = MovieReviewBinding::inflate

    override fun getViewModelClass(): Class<MovieReviewPageViewModel> =
        MovieReviewPageViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getString("movieId", "").toString() // Retrieve the movieId from arguments
        }
        initViews()
        viewmodel.getMovieReviews(movieId, "FAN_ART", "1")

    }


    private fun initViews() {
        updateReviewList()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun updateReviewList() {
        val layoutManager = object : GridLayoutManager(context, 3) {
            override fun canScrollVertically(): Boolean {
                return false
            }

            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        reviewsAdapter = MovieReviewListAdapter(MovieReviewListAdapter.MovieReviewClick {

        })
        binding.moviePosterAdapter.layoutManager = layoutManager
        val gridDecoration = GridItemDecoration(
            3, 20, false
        )
        binding.moviePosterAdapter.addItemDecoration(gridDecoration)
        binding.moviePosterAdapter.adapter = reviewsAdapter
    }


    override fun observeState(state: MovieReviewPageState) {
        when (state) {
            is MovieReviewPageState.GetMovieReviews -> {
                reviewsAdapter.submitList(state.response.items)
            }
        }
    }
}