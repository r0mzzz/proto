package com.example.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MovieModel
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
    var movieList = MutableLiveData<List<MovieModel>?>(null)


     fun getNewMovies(
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

    fun getMovies(
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