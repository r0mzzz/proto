package com.example.home.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.usecase.movies.GetMoviesUseCase
import com.example.home.effect.HomePageEffect
import com.example.home.state.HomePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel<HomePageState, HomePageEffect>() {

    var dominantColor: Int = 0

    init {
        getMovies(null, "2023")
    }

    private fun getMovies(
        type: String? = null,
        yearFrom: String? = null,
        yearTo: String? = null,
        ratingFrom: String? = null,
        ratingTo: String? = null
    ) {
        getMoviesUseCase.launch(
            GetMoviesUseCase.Params(
                type,
                yearFrom,
                yearTo,
                ratingFrom,
                ratingTo
            )
        ) {
            onSuccess = {
                postState(state = HomePageState.GetMoviesList(it))
            }
            onError = {
                postState(state = HomePageState.ErrorOnMovieList(it))
            }
        }
    }
}