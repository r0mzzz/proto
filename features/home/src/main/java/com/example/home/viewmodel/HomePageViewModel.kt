package com.example.home.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MovieModel
import com.example.domain.entity.home.MoviesResponse
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
    var movieList = arrayListOf<MovieModel>()
    var newMovieList = arrayListOf<MovieModel>()

    init {
        getMovies(MovieType.FILM, "2023")
        getNewMovies(MovieType.FILM, "2022", "2023", "7", "10")
    }

    private fun getNewMovies(
        type: MovieType? = null,
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
                postState(state = HomePageState.GetNewMoviesList(it))
            }
            onError = {
                postState(state = HomePageState.ErrorOnMovieList(it))
            }
        }
    }

    private fun getMovies(
        type: MovieType? = null,
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