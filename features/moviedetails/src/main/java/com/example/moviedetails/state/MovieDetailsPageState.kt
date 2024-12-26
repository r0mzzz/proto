package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.MovieTrailerModel

sealed class MovieDetailsPageState {
    class GetMovieDetailSuccess(val response: MovieDetailsModel) : MovieDetailsPageState()
    class GetMovieTrailerSuccess(val response: MovieTrailerModel) : MovieDetailsPageState()
    class GetMovieStuffSuccess(val response: List<MovieStuffModel>) : MovieDetailsPageState()
}