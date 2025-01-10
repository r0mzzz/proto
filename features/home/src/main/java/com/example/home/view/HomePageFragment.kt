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
import com.example.domain.decorations.GridItemDecoration
import com.example.domain.decorations.MarginItemDecoration
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.Genre
import com.example.domain.entity.home.MovieModel
import com.example.home.adapter.GenreAdapter
import com.example.home.adapter.MovieListAdapter
import com.example.home.databinding.FragmentHomePageBinding
import com.example.home.effect.HomePageEffect
import com.example.home.state.HomePageState
import com.example.home.viewmodel.HomePageViewModel
import com.example.uikit.extensions.loadImageFromGLideRounded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        updateMovieOfTheDay(viewmodel.movieList.value?.get(10) ?: MovieModel())
        if (viewmodel.movieList.value == null) {
            viewmodel.getMovies(MovieType.FILM, "1994", "1994", "8", "10")
            viewmodel.movieList.value?.get(10)?.let { it1 -> updateMovieOfTheDay(it1) }
        } else {
            movieListAdapter.submitList(viewmodel.movieList.value)
        }
        if (viewmodel.newMovieList.value == null) {
            viewmodel.getNewMovies(MovieType.FILM, "2009", "2010", "8", "10")
        } else {
            newMoviesListAdapter.submitList(viewmodel.newMovieList.value)
        }
        handleToolbarBackgroundOnScroll()
        viewmodel.toolbarColor.observe(viewLifecycleOwner) { toolbarColor ->
            setToolbarColorWithTransition(toolbarColor)
        }
    }


    private fun handleToolbarBackgroundOnScroll() {
        val scrollView = binding.scrollbar
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val scrollFraction = scrollY.toFloat() / maxScroll
            if (scrollY == 0) {
                resetToolbarColorWithTransition()
            } else {
                val toolbarColor = interpolateColor(
                    viewmodel.dominantColor,
                    Color.parseColor("#99000000"),
                    scrollFraction
                )
                setToolbarColorWithTransition(toolbarColor)
                viewmodel.setToolbarColor(toolbarColor)
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
        val itemDecoration = MarginItemDecoration(15, 1)
        binding.movieListAdapter.layoutManager = layoutManager
        binding.movieListAdapter.addItemDecoration(itemDecoration)
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
        val itemDecoration = MarginItemDecoration(15, 1)
        binding.newListAdapter.layoutManager = layoutManager
        binding.newListAdapter.addItemDecoration(itemDecoration)
        binding.newListAdapter.adapter = newMoviesListAdapter
    }

    private fun handleMovieOfTheDayClick(movieId: Int) {
        binding.movieOfTheDayPoster.setOnClickListener {
            viewmodel.navigate(
                NavigationCommand.Deeplink(
                    "com.example://movieDetails/{movieId}",
                    mutableMapOf("movieId" to movieId.toString()),
                    true
                )
            )
        }
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
        movie.kinopoiskId?.let { handleMovieOfTheDayClick(it) }
    }

    private fun postDelayed(action: () -> Unit) {
        binding.movieOfTheDayPoster.post {
            action()
        }
    }

    private fun setBackgroundColor() {
        val imageView: ImageView = binding.movieOfTheDayPoster
        val layout: View = binding.wrapperBackground
        val drawable = imageView.drawable

        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap

            CoroutineScope(Dispatchers.Default).launch {
                val palette = Palette.from(bitmap).generate()
                val dominantColor = palette.getDominantColor(
                    resources.getColor(com.example.uikit.R.color.black, null)
                )

                val secondaryColor = palette.getMutedColor(
                    resources.getColor(com.example.uikit.R.color.black, null)
                )

                withContext(Dispatchers.Main) {
                    val fadeGradient = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(dominantColor, secondaryColor, 0xFF000000.toInt())
                    )
                    fadeGradient.gradientType = GradientDrawable.LINEAR_GRADIENT
                    val layerDrawable = LayerDrawable(arrayOf(drawable, fadeGradient))

                    layout.background = layerDrawable
                }
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
                    viewmodel.movieList.value?.get(10)?.let { it1 -> updateMovieOfTheDay(it1) }
                }

            }

            is HomePageState.GetNewMoviesList -> {
                state.response.items?.let {
                    viewmodel.newMovieList.value = state.response.items
                    newMoviesListAdapter.submitList(it)
                }
            }
        }
    }
}