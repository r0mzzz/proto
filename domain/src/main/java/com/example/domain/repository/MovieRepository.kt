package com.example.domain.repository

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.services.MovieService
import retrofit2.Response
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovies(
        type: MovieType? = null,
        yearFrom: String? = null,
        yearTo: String? = null,
        ratingFrom: String? = null,
        ratingTo: String? = null
    ): MoviesResponse
}