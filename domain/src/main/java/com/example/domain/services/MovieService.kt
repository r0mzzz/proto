package com.example.domain.services

import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("films")
    suspend fun getMovies(
        @Query("type") type: MovieType? = null,
        @Query("ratingFrom") ratingFrom: String? = null,
        @Query("ratingTo") ratingTo: String? = null,
        @Query("yearFrom") yearFrom: String? = null,
        @Query("yearTo") yearTo: String? = null,
    ): MoviesResponse
}



