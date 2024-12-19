package com.example.home.view

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.HandlerCompat.postDelayed
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import com.example.uikit.extensions.loadImageFromGLideRounded
import com.example.uikit.extensions.loadImageFromGlide
import com.example.uikit.toolbar.MyToolbar
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
        handleToolbarBackgroundOnScroll()
    }

    private fun handleToolbarBackgroundOnScroll() {
        val scrollView = binding.scrollbar
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val scrollFraction = scrollY.toFloat() / maxScroll
            if (scrollY == 0) {
                resetToolbarColorWithTransition()
            } else {
                // Adjust toolbar color based on scroll position (scrollFraction goes from 0 to 1)
                val toolbarColor = interpolateColor(viewmodel.dominantColor, Color.parseColor("#BE000000"), scrollFraction)
                setToolbarColorWithTransition(toolbarColor)
            }
            // Adjust toolbar color based on scroll position (scrollFraction goes from 0 to 1)
        }
    }

    private fun resetToolbarColorWithTransition() {
        // Reset the toolbar color to the default (transparent)
        val defaultColor = viewmodel.dominantColor
        setToolbarColorWithTransition(defaultColor)
    }

    private fun setToolbarColorWithTransition(color: Int) {
        // Extract the current color from the toolbar's background (if it's a ColorDrawable)
        val currentColor =
            (binding.toolbar.background as? ColorDrawable)?.color ?: Color.TRANSPARENT

        // Animate the color change smoothly
        val colorAnimator = ValueAnimator.ofArgb(currentColor, color)
        colorAnimator.duration = 300 // 300ms for a smooth transition
        colorAnimator.addUpdateListener { animator ->
            binding.toolbar.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimator.start()
    }

    private fun interpolateColor(colorStart: Int, colorEnd: Int, fraction: Float): Int {
        val startAlpha = (colorStart shr 24) and 0xff
        val startRed = (colorStart shr 16) and 0xff
        val startGreen = (colorStart shr 8) and 0xff
        val startBlue = colorStart and 0xff

        val endAlpha = (colorEnd shr 24) and 0xff
        val endRed = (colorEnd shr 16) and 0xff
        val endGreen = (colorEnd shr 8) and 0xff
        val endBlue = colorEnd and 0xff

        val alpha = (startAlpha + (endAlpha - startAlpha) * fraction).toInt()
        val red = (startRed + (endRed - startRed) * fraction).toInt()
        val green = (startGreen + (endGreen - startGreen) * fraction).toInt()
        val blue = (startBlue + (endBlue - startBlue) * fraction).toInt()

        return Color.argb(alpha, red, green, blue)
    }

    private fun initViews() {
        binding.apply {
//            toolbar.setToolBarRightActionClick {
//                Toast.makeText(requireContext(), "dsfdsfsdf", Toast.LENGTH_SHORT).show()
//            }
//            toolbar.setTitle("HomePage")
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
        updateMovieOfTheDay(response[2])
    }

    private fun updateMovieOfTheDay(movie: MovieModel) {
        binding.movieOfTheDayPoster.loadImageFromGLideRounded(
            movie.posterUrl.toString(),
            24,
            object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    postDelayed {
                        setBackgroundColor()
                    }
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })

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

    private fun postDelayed(action: () -> Unit) {
        binding.movieOfTheDayPoster.post {
            action()
        }
    }

    private fun setBackgroundColor() {
        val imageView: ImageView? = binding.root.findViewById(R.id.movie_of_the_day_poster)
        val layout: ImageView? = binding.root.findViewById(R.id.wrapper_background)
        val toolbar: MyToolbar? = binding.root.findViewById(R.id.toolbar)
        val drawable = imageView?.drawable

        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap
            Palette.from(bitmap).generate { palette ->
                val dominantColor = palette?.getDominantColor(
                    resources.getColor(com.example.uikit.R.color.black, null)
                ) ?: resources.getColor(com.example.uikit.R.color.black, null)

                val secondaryColor = palette?.getMutedColor(
                    resources.getColor(com.example.uikit.R.color.black, null)
                ) ?: resources.getColor(com.example.uikit.R.color.black, null)

                // Create a gradient drawable with the two colors
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(dominantColor, secondaryColor)
                )

                // Apply gradient to the background
                layout?.background = gradientDrawable
                toolbar?.background = gradientDrawable
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