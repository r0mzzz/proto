package com.example.home.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.domain.entity.home.Genre
import com.example.domain.entity.home.MovieModel
import com.example.home.R
import com.example.home.adapter.GenreAdapter
import com.example.home.adapter.MovieListAdapter
import com.example.home.databinding.FragmentHomePageBinding
import com.example.home.effect.HomePageEffect
import com.example.home.state.HomePageState
import com.example.home.viewmodel.HomePageViewModel
import com.example.uikit.extensions.loadImageFromGLide
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
        updateMovieOfTheDay(response[5])
    }

    private fun updateMovieOfTheDay(movie: MovieModel) {
        binding.movieOfTheDayPoster.loadImageFromGLide(movie.posterUrl.toString())
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
        setBackgroundColor()
    }

    private fun setBackgroundColor(){
        val imageView: ImageView? = binding.root.findViewById(R.id.movie_of_the_day_poster)
        val layout: ConstraintLayout? = binding.root.findViewById(R.id.homePageFragment)
        val drawable = imageView?.drawable
        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap
            Palette.from(bitmap).generate { palette ->
                val dominantColor = palette?.getDominantColor(resources.getColor(com.example.uikit.R.color.black, null))
                Log.d("dominantColor",dominantColor.toString())
                if (dominantColor != null) {
                    layout?.setBackgroundColor(dominantColor)
                }
            }
        }

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