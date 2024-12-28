package com.example.moviedetails.viewmodel

import com.example.core.base.BaseViewModel
import com.example.moviedetails.effect.SimilarMoviesPageEffect
import com.example.moviedetails.state.SimilarMoviesPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(


) : BaseViewModel<SimilarMoviesPageState, SimilarMoviesPageEffect>() {


}