package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.MovieReviewModel

sealed class MovieReviewPageState {

    class GetMovieReviews(val response: MovieReviewModel) : MovieReviewPageState()
}