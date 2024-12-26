package com.example.home.view

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.domain.entity.enums.MovieType
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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment :
    BaseFragment<HomePageState, HomePageEffect, FragmentHomePageBinding, HomePageViewModel>() {

    override fun getViewModelClass(): Class<HomePageViewModel> = HomePageViewModel::class.java
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var newMoviesListAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        if (viewmodel.movieList.value == null) {
            viewmodel.getMovies(MovieType.FILM, "1994", "1994", "8", "10")
        } else {
            movieListAdapter.submitList(viewmodel.movieList.value)
        }
        if (viewmodel.newMovieList.value == null) {
            viewmodel.getNewMovies(MovieType.FILM, "2009", "2010", "8", "10")
            viewmodel.newMovieList.value?.get(19)?.let { it1 -> updateMovieOfTheDay(it1) }
        } else {
            newMoviesListAdapter.submitList(viewmodel.newMovieList.value)
        }
        updateMovieOfTheDay(viewmodel.newMovieList.value?.get(10) ?: MovieModel())
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
                val toolbarColor = interpolateColor(
                    viewmodel.dominantColor,
                    Color.parseColor("#99000000"),
                    scrollFraction
                )
                setToolbarColorWithTransition(toolbarColor)
            }
        }
    }

    private fun resetToolbarColorWithTransition() {
        val defaultColor = viewmodel.dominantColor
        setToolbarColorWithTransition(defaultColor)
    }

    private fun setToolbarColorWithTransition(color: Int) {
        val currentColor =
            (binding.toolbar.background as? ColorDrawable)?.color ?: Color.TRANSPARENT

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
        initMovieListAdapter()
        initNewMovieListAdapter()
    }

    private fun initMovieListAdapter() {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        movieListAdapter =
            MovieListAdapter(
                MovieListAdapter.MovieItemClick {
                    viewmodel.navigate(
                        NavigationCommand.Deeplink(
                            "com.example://movieDetails/{movieId}",
                            mutableMapOf("movieId" to it.kinopoiskId.toString()),
                            true
                        )
                    )
                })
        binding.movieListAdapter.layoutManager = layoutManager
        binding.movieListAdapter.adapter = movieListAdapter
    }

    private fun initNewMovieListAdapter() {

        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        newMoviesListAdapter =
            MovieListAdapter(
                MovieListAdapter.MovieItemClick {
                    viewmodel.navigate(
                        NavigationCommand.Deeplink(
                            "com.example://movieDetails/{movieId}",
                            mutableMapOf("movieId" to it.kinopoiskId.toString()),
                            true
                        )
                    )
                })
        binding.newListAdapter.layoutManager = layoutManager
        binding.newListAdapter.adapter = newMoviesListAdapter
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

        val genres = movie.genres?.map { Genre(it.genre) }?.take(3) ?: emptyList()
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
        val layout: View? = binding.root.findViewById(R.id.wrapper_background)
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

                val fadeGradient = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        dominantColor,
                        secondaryColor,
                        0xFF000000.toInt()
                    )
                )
                fadeGradient.gradientType = GradientDrawable.LINEAR_GRADIENT
                val layerDrawable = LayerDrawable(arrayOf(drawable, fadeGradient))
                layerDrawable.setLayerGravity(1, android.view.Gravity.BOTTOM)
                layout?.background = layerDrawable
            }
        }
    }


    override fun observeState(state: HomePageState) {
        when (state) {
            is HomePageState.ErrorOnMovieList -> {
            }

            is HomePageState.GetMoviesList -> {
                state.response.items?.let {
                    viewmodel.movieList.value = state.response.items
                    movieListAdapter.submitList(it)
                }

            }

            is HomePageState.GetNewMoviesList -> {
                state.response.items?.let {
                    viewmodel.newMovieList.value = state.response.items
                    newMoviesListAdapter.submitList(it)
                    viewmodel.newMovieList.value?.get(19)?.let { it1 -> updateMovieOfTheDay(it1) }
                }
            }
        }
    }
}