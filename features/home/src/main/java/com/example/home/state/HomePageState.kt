package com.example.home.state

import com.example.domain.entity.home.MoviesResponse

sealed class HomePageState {
    class GetMoviesList(val response: MoviesResponse): HomePageState()
    class GetNewMoviesList(val response: MoviesResponse): HomePageState()
    class ErrorOnMovieList(val error: Throwable): HomePageState()
}