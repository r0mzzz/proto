package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.usecase.movies.GetMovieDetailsUseCase
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase

) : BaseViewModel<MovieDetailsPageState, MovieDetailsPageEffect>() {


    fun getMovieDetail(id: String) {
        getMovieDetailUseCase.launch(GetMovieDetailsUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieDetailSuccess(it))
            }
        }
    }
}