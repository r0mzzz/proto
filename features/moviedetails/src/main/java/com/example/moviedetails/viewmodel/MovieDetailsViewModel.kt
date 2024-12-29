package com.example.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.domain.entity.home.MovieModel
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.TrailerItems
import com.example.domain.usecase.movies.GetMovieDetailsUseCase
import com.example.domain.usecase.movies.GetMovieStuffUseCase
import com.example.domain.usecase.movies.GetMovieTrailerUseCase
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieStuffUseCase: GetMovieStuffUseCase

) : BaseViewModel<MovieDetailsPageState, MovieDetailsPageEffect>() {
    var movieDetails = MutableLiveData<MovieDetailsModel>(null)
    var currentMovieId = ""
    var previousMovieId = ""

    fun getMovieDetail(id: String) {
        getMovieDetailUseCase.launchNoLoading(GetMovieDetailsUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieDetailSuccess(it))
            }
        }
    }

    fun getMovieTrailer(id: String) {
        getMovieTrailerUseCase.launchNoLoading(GetMovieTrailerUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieTrailerSuccess(it))
            }
        }
    }

    fun getMovieStuff(id: String) {
        getMovieStuffUseCase.launchNoLoading(GetMovieStuffUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieStuffSuccess(it))
            }
        }
    }
}