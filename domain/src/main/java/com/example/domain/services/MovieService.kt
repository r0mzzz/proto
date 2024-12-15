package com.example.domain.services

import com.example.domain.entity.home.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET("films")
    suspend fun getMovies(): MoviesResponse
}