package com.example.domain.services

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.MoviePosterModel
import com.example.domain.entity.moviedetails.MovieStuffModel
import com.example.domain.entity.moviedetails.MovieTrailerModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("v2.2/films")
    suspend fun getMovies(
        @Query("type") type: MovieType? = null,
        @Query("yearFrom") ratingFrom: String? = null,
        @Query("yearTo") ratingTo: String? = null,
        @Query("ratingFrom") yearFrom: String? = null,
        @Query("ratingTo") yearTo: String? = null,
    ): MoviesResponse

    @GET("v2.2/films/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String? = null,
    ): MovieDetailsModel

    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Path("id") id: String? = null,
    ): SimilarMoviesModel

    @GET("v2.2/films/{id}/images")
    suspend fun getMoviePosters(
        @Path("id") id: String? = null,
        @Query("type") type: String? = null,
        @Query("page") page: String? = null,
    ): MoviePosterModel

    @GET("v2.2/films/{id}/videos")
    suspend fun getMovieTrailer(
        @Path("id") id: String? = null,
    ): MovieTrailerModel

    @GET("v1/staff")
    suspend fun getMovieStuff(
        @Query("filmId") id: String? = null,
    ): List<MovieStuffModel>
}


