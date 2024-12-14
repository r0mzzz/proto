package com.example.proto.network

import com.example.domain.entity.home.MovieItemModel
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET("films")
    suspend fun getMovies(): Response<Any>
}