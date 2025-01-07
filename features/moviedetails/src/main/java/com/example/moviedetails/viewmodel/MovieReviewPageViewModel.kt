package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.domain.usecase.movies.GetMoviePostersUseCase
import com.example.moviedetails.effect.MovieReviewPageEffect
import com.example.moviedetails.state.MovieReviewPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieReviewPageViewModel @Inject constructor(
    private val getMoviePostersUseCase: GetMoviePostersUseCase


) : BaseViewModel<MovieReviewPageState, MovieReviewPageEffect>() {


    fun getMovieReviews(id: String, type: String, page: String){
        getMoviePostersUseCase.launchNoLoading(GetMoviePostersUseCase.Params(id, type, page)){
            onSuccess = {
                postState(state = MovieReviewPageState.GetMovieReviews(it))
            }
        }
    }

}