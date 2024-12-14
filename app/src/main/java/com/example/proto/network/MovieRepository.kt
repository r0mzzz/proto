package com.example.proto.network

import com.example.domain.entity.home.MoviesResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun getMovies(): Response<MoviesResponse> {
        return movieService.getMovies()
    }
}