package com.example.moviedetails.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.core.base.BaseFragment
import com.example.domain.database.entity.LikedMovies
import com.example.domain.database.entity.MyList
import com.example.domain.entity.enums.SegmentTabs
import com.example.domain.entity.enums.StaffType
import com.example.domain.entity.models.ViewPagerTabModel
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.TrailerItems
import com.example.moviedetails.adapter.ViewPagerAdapter
import com.example.moviedetails.databinding.FragmentMovieDetailsBinding
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import com.example.moviedetails.viewmodel.MovieDetailsViewModel
import com.example.uikit.extensions.gone
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<MovieDetailsPageState, MovieDetailsPageEffect, FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private lateinit var similarViewPagerAdapter: ViewPagerAdapter

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
        buildSegmentTabs()
    }

    private fun buildSegmentTabs() {
        val items = listOf(
            ViewPagerTabModel(
                args.movieId,
                "Похожие",
                com.example.moviedetails.R.id.similar_movies_adapter,
                SegmentTabs.SIMILAR.name,
            ),
            ViewPagerTabModel(
                args.movieId,
                "Постеры",
                com.example.moviedetails.R.id.movie_poster_adapter,
                SegmentTabs.POSTERS.name,
            ),
        )
        loadContent(items)
    }

    private fun loadContent(items: List<ViewPagerTabModel>) {
        similarViewPagerAdapter = ViewPagerAdapter(items, childFragmentManager, lifecycle)
        binding.similarMoviesViewpager.adapter = similarViewPagerAdapter
        binding.similarMoviesViewpager.isUserInputEnabled = false

        binding.similarMoviesViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                println("Page selected: $position")
            }
        })

        TabLayoutMediator(binding.tabs, binding.similarMoviesViewpager) { tab, position ->
            tab.text = items[position].name
        }.attach()
        for (i in items.indices) {
            val headerTab = LayoutInflater.from(requireContext())
                .inflate(com.example.moviedetails.R.layout.tab_item, null)
            binding.tabs.getTabAt(i)?.customView = headerTab
            headerTab.findViewById<TextView>(com.example.moviedetails.R.id.tab_title).text =
                items[i].name
        }
    }


    private fun initViews() {
        viewmodel.currentMovieId.value = args.movieId
        if (viewmodel.currentMovieId.value != viewmodel.previousMovieId.value) {
            viewmodel.previousMovieId.value = viewmodel.currentMovieId.value
            viewmodel.getMovieDetail(args.movieId)
        } else {
            viewmodel.movieDetails.value?.let {
                updateMovieDetails(it)
            }
        }
        with(binding) {
            rateContainer.setOnClickListener {
                viewmodel.likeMovie(
                    LikedMovies(
                        id = viewmodel.currentMovieId.value!!.toInt(),
                        movieTitle = viewmodel.movieDetails.value?.nameOriginal.toString(),
                        moviePoster = viewmodel.movieDetails.value?.posterUrl.toString()
                    )
                )
            }
            listBtn.setOnClickListener {
                viewmodel.addItem(
                    MyList(
                        id = viewmodel.currentMovieId.value!!.toInt(),
                        movieTitle = viewmodel.movieDetails.value?.nameOriginal.toString(),
                        moviePoster = viewmodel.movieDetails.value?.posterUrl.toString()
                    )
                )
            }
        }
    }


    private fun updateMovieDetails(response: MovieDetailsModel) {
        binding.movieTitle.text = response.nameEn ?: response.nameRu
        if (response.year != null) {
            binding.year.text = response.year.toString()
        }
        binding.description.text = response.shortDescription
        if (response.ratingAgeLimits.isNullOrEmpty()) {
            binding.ageLimit.text = response.ratingAgeLimits?.replace("age", "").plus("+")
        }
        if (response.filmLength != null) {
            binding.filmLength.text = convertMinutesToFilmLength(response.filmLength.toString())
        }
        binding.blackScreen.gone()
    }


    private fun collectStuff(staff: List<MovieStuffModel>) {
        val actorNames = mutableListOf<String>()
        staff.filter { it.professionKey == StaffType.ACTOR.name }.take(8).forEach {
            actorNames.add(it.nameRu.toString())
        }
        binding.actor.text = actorNames.joinToString(", ")
        binding.director.text =
            staff.filter { it.professionKey == StaffType.DIRECTOR.name }.take(1)[0].nameRu

    }

    private fun convertMinutesToFilmLength(totalMinutes: String): String {
        val hours = totalMinutes.toInt() / 60
        val minutes = totalMinutes.toInt() % 60
        val time = "$hours ч. $minutes мин."
        return time
    }

    fun extractVideoId(url: String): String? {
        val regex = "/v/([^?&/]+)".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value
    }

    private fun updateVideo(response: List<TrailerItems>?) {
        var videoUrl = ""

        response?.forEach {
            if (it.site == "YOUTUBE") {
                videoUrl = it.url.toString()
                Log.i("Video URL", videoUrl)
            }
        }

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = extractVideoId(videoUrl)
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                    val defaultPlayerUiController =
                        DefaultPlayerUiController(binding.player, youTubePlayer)
                    defaultPlayerUiController.showVideoTitle(false)
                    defaultPlayerUiController.showUi(false)
                    defaultPlayerUiController.showMenuButton(false)
                    defaultPlayerUiController.showYouTubeButton(false)
                }
            }
        }

        val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(0) // Hide controls
            .fullscreen(0) // Disable fullscreen option
            .autoplay(1)
            .mute(1)
            .build()
        binding.player.initialize(listener, iFramePlayerOptions)
    }


    override fun observeState(state: MovieDetailsPageState) {
        when (state) {
            is MovieDetailsPageState.GetMovieDetailSuccess -> {
                state.response.let {
                    if (it != viewmodel.movieDetails.value) {
                        viewmodel.movieDetails.value = state.response
                        viewmodel.getMovieStuff(state.response.kinopoiskId.toString())
                        viewmodel.getMovieTrailer(state.response.kinopoiskId.toString())
                        updateMovieDetails(state.response)
                    }
                }
            }

            is MovieDetailsPageState.GetMovieTrailerSuccess -> {
                updateVideo(state.response.items)
            }

            is MovieDetailsPageState.GetMovieStuffSuccess -> {
                collectStuff(state.response)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.player.release()
    }
}