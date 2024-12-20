package com.example.domain.repository

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.services.MovieService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {

    override suspend fun getMovies(
        type: MovieType?,
        yearFrom: String?,
        yearTo: String?,
        ratingFrom: String?,
        ratingTo: String?
    ): MoviesResponse {
        return movieService.getMovies(type, yearFrom, yearTo, ratingFrom, ratingTo)
    }

    override suspend fun getMovieDetails(id: String): MovieDetailsModel {
        return movieService.getMovieDetails(id)
    }

}