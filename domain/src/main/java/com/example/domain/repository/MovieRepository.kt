package com.example.domain.repository

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MoviePosterModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.MovieTrailerModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel

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
    suspend fun getMovieStuff(id: String): List<MovieStuffModel>
    suspend fun getSimilarMovies(id: String): SimilarMoviesModel
    suspend fun getMoviePosters(id: String, type: String, page: String): MoviePosterModel
}