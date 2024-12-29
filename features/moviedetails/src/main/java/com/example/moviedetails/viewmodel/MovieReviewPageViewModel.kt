package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.moviedetails.effect.MovieReviewPageEffect
import com.example.moviedetails.state.MovieReviewPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieReviewPageViewModel @Inject constructor(


) : BaseViewModel<MovieReviewPageState, MovieReviewPageEffect>() {


}