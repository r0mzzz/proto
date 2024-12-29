package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.SimilarMovieModel

sealed class SimilarMoviesPageState {
    class GetSimilarMovies(val response: List<SimilarMovieModel>) : SimilarMoviesPageState()

}