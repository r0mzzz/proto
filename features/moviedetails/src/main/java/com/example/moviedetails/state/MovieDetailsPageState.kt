package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.MovieDetailsModel

sealed class MovieDetailsPageState {
    class GetMovieDetailSuccess(val response: MovieDetailsModel) : MovieDetailsPageState()
}