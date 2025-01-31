package com.example.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.domain.decorations.GridItemDecoration
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
            movieId = it.getString("movieId", "").toString() // Retrieve the movieId from arguments
        }
        initViews()
        viewmodel.getSimilarMovies(movieId)
    }


    private fun initViews() {
        initSimilarMovieList()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun initSimilarMovieList() {
        val layoutManager = object : GridLayoutManager(context, 3) {
            override fun canScrollVertically(): Boolean {
                return false
            }

            override fun canScrollHorizontally(): Boolean {
                return false
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
        val gridDecoration = GridItemDecoration(
            3, 20, false
        )
        binding.similarMoviesAdapter.addItemDecoration(gridDecoration)
        binding.similarMoviesAdapter.adapter = similarMovieListAdapter
    }


    override fun observeState(state: SimilarMoviesPageState) {
        when (state) {
            is SimilarMoviesPageState.GetSimilarMovies -> {
                similarMovieListAdapter.submitList(state.response.items)
            }
        }
    }

}