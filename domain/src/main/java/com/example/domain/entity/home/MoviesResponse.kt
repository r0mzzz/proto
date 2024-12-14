package com.example.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val total: Int? = null,
    val totalPages: Int? = null,
    val items: List<MovieModel>? = null
)

@Serializable
data class MovieModel(
    val kinopoiskId: Int? = null,
    val imdbId: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val countries: List<Country>? = null,
    val genres: List<Genre>? = null,
    val ratingKinopoisk: Double? = null,
    val ratingImdb: Double? = null,
    val year: Int? = null,
    val type: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null
) : java.io.Serializable

@Serializable
data class Country(
    val country: String? = null
)

@Serializable
data class Genre(
    val genre: String? = null
)
