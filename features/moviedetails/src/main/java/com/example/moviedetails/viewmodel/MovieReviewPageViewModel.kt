package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.usecase.movies.GetMovieReviewsUseCase
import com.example.moviedetails.effect.MovieReviewPageEffect
import com.example.moviedetails.state.MovieReviewPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieReviewPageViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase


) : BaseViewModel<MovieReviewPageState, MovieReviewPageEffect>() {


    fun getMovieReviews(id: String){
        getMovieReviewsUseCase.launchNoLoading(GetMovieReviewsUseCase.Params(id)){
            onSuccess = {
                postState(state = MovieReviewPageState.GetMovieReviews(it))
            }
        }
    }

}