package com.example.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.core.base.BaseFragment
import com.example.domain.entity.enums.StaffType
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.TrailerItems
import com.example.moviedetails.databinding.FragmentMovieDetailsBinding
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import com.example.moviedetails.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<MovieDetailsPageState, MovieDetailsPageEffect, FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private val args by navArgs<MovieDetailsFragmentArgs>()


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
    }

    private fun initViews() {
        with(binding) {
        }
        viewmodel.currentMovieId = args.movieId
        if (viewmodel.currentMovieId !== viewmodel.previousMovieId) {
            viewmodel.previousMovieId = viewmodel.currentMovieId
            if (viewmodel.movieDetails.value?.kinopoiskId?.toString() != viewmodel.currentMovieId) {
                viewmodel.getMovieDetail(args.movieId)
            } else {
                updateMovieDetails(viewmodel.movieDetails.value!!)
            }
        }
    }

    private fun updateMovieDetails(response: MovieDetailsModel) {
        binding.movieTitle.text = response.nameEn ?: response.nameRu
        binding.year.text = response.year.toString()
        binding.filmLength.text = convertMinutesToFilmLength(response.filmLength.toString())
        binding.description.text = response.shortDescription
        binding.ageLimit.text = response.ratingAgeLimits?.replace("age", "").plus("+")
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
        webSettings.javaScriptEnabled = true
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