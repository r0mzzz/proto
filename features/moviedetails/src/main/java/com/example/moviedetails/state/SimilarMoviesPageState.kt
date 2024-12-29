package com.example.moviedetails.state

import com.example.domain.entity.moviedetails.SimilarMoviesModel

sealed class SimilarMoviesPageState {
    class GetSimilarMovies(val response: SimilarMoviesModel) : SimilarMoviesPageState()

}