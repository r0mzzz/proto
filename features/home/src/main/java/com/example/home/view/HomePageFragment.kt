package com.example.home.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.domain.entity.home.Genre
import com.example.domain.entity.home.MovieItemModel
import com.example.domain.entity.home.MovieModel
import com.example.home.adapter.GenreAdapter
import com.example.home.adapter.MovieListAdapter
import com.example.home.databinding.FragmentHomePageBinding
import com.example.home.effect.HomePageEffect
import com.example.home.state.HomePageState
import com.example.home.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment :
    BaseFragment<HomePageState, HomePageEffect, FragmentHomePageBinding, HomePageViewModel>() {

    override fun getViewModelClass(): Class<HomePageViewModel> = HomePageViewModel::class.java
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate
    private lateinit var movieListAdapter: MovieListAdapter

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
    }

    private fun initMovieListAdapter(response: List<MovieModel>) {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        movieListAdapter =
            MovieListAdapter(response, MovieListAdapter.MovieItemClick {
                viewmodel.navigate(
                    NavigationCommand.Deeplink(
                        "com.example://movieDetails",
                        null,
                        true
                    )
                )
            })
        binding.movieListAdapter.adapter = movieListAdapter
        binding.movieListAdapter.layoutManager = layoutManager
        updateMovieOfTheDay(response[0])
    }

    private fun updateMovieOfTheDay(movie: MovieModel) {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }

            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        val genres = movie.genres?.map { Genre(it.genre) } ?: emptyList()
        val adapter = GenreAdapter(genres)
        binding.genreList.adapter = adapter
        binding.genreList.layoutManager = layoutManager
    }


    override fun observeState(state: HomePageState) {
        when (state) {
            is HomePageState.ErrorOnMovieList -> {
            }

            is HomePageState.GetMoviesList -> {
                state.response.items?.let { initMovieListAdapter(it) }
            }
        }
    }
}