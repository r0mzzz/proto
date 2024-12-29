package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.usecase.movies.GetSimilarMoviesUseCase
import com.example.moviedetails.effect.SimilarMoviesPageEffect
import com.example.moviedetails.state.SimilarMoviesPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase

) : BaseViewModel<SimilarMoviesPageState, SimilarMoviesPageEffect>() {


    fun getSimilarMovies(id: String) {
        getSimilarMoviesUseCase.launchNoLoading(GetSimilarMoviesUseCase.Params(id)) {
            onSuccess = {
                postState(SimilarMoviesPageState.GetSimilarMovies(it))
            }
        }
    }

}