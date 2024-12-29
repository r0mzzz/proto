package com.example.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.moviedetails.adapter.SimilarMovieListAdapter
import com.example.moviedetails.databinding.SimilarLayoutBinding
import com.example.moviedetails.effect.SimilarMoviesPageEffect
import com.example.moviedetails.state.SimilarMoviesPageState
import com.example.moviedetails.viewmodel.SimilarMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SimilarMoviesFragment :
    BaseFragment<SimilarMoviesPageState, SimilarMoviesPageEffect, SimilarLayoutBinding, SimilarMoviesViewModel>() {
    private lateinit var similarMovieListAdapter: SimilarMovieListAdapter
    private var movieId = ""
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> SimilarLayoutBinding
        get() = SimilarLayoutBinding::inflate

    override fun getViewModelClass(): Class<SimilarMoviesViewModel> =
        SimilarMoviesViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getInt("movieId", 0).toString() // Retrieve the movieId from arguments
        }
        initViews()
        viewmodel.getSimilarMovies(movieId)
    }


    private fun initViews() {
        with(binding) {
        }
        initSimilarMovieList()
    }

    private fun initSimilarMovieList() {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        similarMovieListAdapter =
            SimilarMovieListAdapter(
                SimilarMovieListAdapter.MovieItemClick {
                    viewmodel.navigate(
                        NavigationCommand.Deeplink(
                            "com.example://movieDetails/{movieId}",
                            mutableMapOf("movieId" to it.filmId.toString()),
                            true
                        )
                    )
                })
        binding.similarMoviesAdapter.layoutManager = layoutManager
        binding.similarMoviesAdapter.adapter = similarMovieListAdapter
    }


    override fun observeState(state: SimilarMoviesPageState) {
        when (state) {
            is SimilarMoviesPageState.GetSimilarMovies -> {
                state.response.let {
                    similarMovieListAdapter.submitList(it)
                }
            }
        }
    }

}