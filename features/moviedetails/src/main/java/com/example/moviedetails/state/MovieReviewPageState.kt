package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.MoviePosterModel


sealed class MovieReviewPageState {

    class GetMovieReviews(val response: MoviePosterModel) : MovieReviewPageState()
}