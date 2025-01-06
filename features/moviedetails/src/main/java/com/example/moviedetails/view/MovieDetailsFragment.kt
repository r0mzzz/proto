package com.example.moviedetails.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.core.base.BaseFragment
import com.example.domain.entity.enums.StaffType
import com.example.domain.entity.models.ViewPagerTabModel
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.domain.entity.moviedetails.TrailerItems
import com.example.moviedetails.adapter.ViewPagerAdapter
import com.example.moviedetails.databinding.FragmentMovieDetailsBinding
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import com.example.moviedetails.viewmodel.MovieDetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator
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
        val items = listOf(
            ViewPagerTabModel(id = args.movieId.toInt(), name = "Similar"),
            ViewPagerTabModel(id = 2, name = "Overviews"),
        )
        loadContent(items)
    }

    private fun loadContent(items: List<ViewPagerTabModel>) {
        similarViewPagerAdapter =
            ViewPagerAdapter(items, childFragmentManager, lifecycle)
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
        with(binding) {
        }

        viewmodel.currentMovieId.value = args.movieId
        if (viewmodel.currentMovieId.value != viewmodel.previousMovieId.value) {
            viewmodel.previousMovieId.value = viewmodel.currentMovieId.value
            viewmodel.getMovieDetail(args.movieId)
        } else {
            viewmodel.movieDetails.value?.let {
                updateMovieDetails(it)
            } ?: run {
            }
        }
    }


    private fun updateMovieDetails(response: MovieDetailsModel) {
        binding.movieTitle.text = response.nameEn ?: response.nameRu
        binding.year.text = response.year.toString()
        binding.filmLength.text = convertMinutesToFilmLength(response.filmLength.toString())
        binding.description.text = response.shortDescription
        binding.ageLimit.text = response.ratingAgeLimits?.replace("age", "").plus("+")
//        binding.blackScreen.gone()
    }


    private fun collectStuff(staff: List<MovieStuffModel>) {
        val actorNames = mutableListOf<String>()
        staff.filter { it.professionKey == StaffType.ACTOR.name }
            .take(8)
            .forEach {
                actorNames.add(it.nameRu.toString())
            }
        binding.actor.text = actorNames.joinToString(", ")
        binding.director.text = staff.filter { it.professionKey == StaffType.DIRECTOR.name }
            .take(1)[0].nameRu

    }

    private fun convertMinutesToFilmLength(totalMinutes: String): String {
        val hours = totalMinutes.toInt() / 60
        val minutes = totalMinutes.toInt() % 60
        val time = "$hours ч. $minutes мин."
        return time
    }

    private fun updateVideo(response: List<TrailerItems>?) {
        var videoUrl = ""
        val webSettings = binding.player.settings
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        binding.player.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        response?.forEach {
            if (it.site == "YOUTUBE") {
                videoUrl = it.url.toString() + "?controls=0&modestbranding=0"
            }
        }
        binding.player.loadUrl(videoUrl)
        binding.player.webChromeClient = WebChromeClient()
    }

    override fun observeState(state: MovieDetailsPageState) {
        when (state) {
            is MovieDetailsPageState.GetMovieDetailSuccess -> {
                viewmodel.movieDetails.value = state.response
                viewmodel.getMovieStuff(state.response.kinopoiskId.toString())
                viewmodel.getMovieTrailer(state.response.kinopoiskId.toString())
                updateMovieDetails(state.response)
            }

            is MovieDetailsPageState.GetMovieTrailerSuccess -> {
                updateVideo(state.response.items)
            }

            is MovieDetailsPageState.GetMovieStuffSuccess -> {
                collectStuff(state.response)
            }
        }
    }
}