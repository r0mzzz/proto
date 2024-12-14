package com.example.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.domain.entity.home.MovieItemModel
import com.example.home.adapter.MovieListAdapter
import com.example.home.databinding.FragmentHomePageBinding
import com.example.home.effect.HomePageEffect
import com.example.home.state.HomePageState
import com.example.home.viewmodel.HomePageViewModel

class HomePageFragment : BaseFragment<HomePageState, HomePageEffect, FragmentHomePageBinding, HomePageViewModel>() {

    override fun getViewModelClass(): Class<HomePageViewModel> = HomePageViewModel::class.java
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate
    private lateinit var movieListAdapter: MovieListAdapter
    private var movieList = emptyList<MovieItemModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            toolbar.setToolBarRightActionClick {
                Toast.makeText(requireContext(), "dsfdsfsdf", Toast.LENGTH_SHORT).show()
            }
            toolbar.setTitle("HomePage")
        }
        initMovieListAdapter()
    }

    private fun initMovieListAdapter() {
        movieList = listOf(
            MovieItemModel(com.example.uikit.R.drawable.movie_poster),
            MovieItemModel(),
            MovieItemModel(),
            MovieItemModel(),
            MovieItemModel(),
            MovieItemModel(),
        )
        movieListAdapter =
            MovieListAdapter(movieList, MovieListAdapter.MovieItemClick {
                viewmodel.navigate(
                    NavigationCommand.Deeplink(
                        "com.example://movieDetails",
                        null,
                        true
                    )
                )
            })
        binding.movieListAdapter.adapter = movieListAdapter
    }

}