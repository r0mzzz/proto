package com.example.domain.repository

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MovieTrailerModel

interface MovieRepository {
    suspend fun getMovies(
        type: MovieType? = null,
        yearFrom: String? = null,
        yearTo: String? = null,
        ratingFrom: String? = null,
        ratingTo: String? = null
    ): MoviesResponse

    suspend fun getMovieDetails(id: String): MovieDetailsModel
    suspend fun getMovieTrailer(id: String): MovieTrailerModel
}