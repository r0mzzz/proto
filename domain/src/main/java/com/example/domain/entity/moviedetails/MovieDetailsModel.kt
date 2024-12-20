package com.example.domain.entity.moviedetails

import com.example.domain.entity.enums.MovieType
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsModel(
    val kinopoiskId: Int? = null,
    val kinopoiskHDId: String? = null,
    val imdbId: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val coverUrl: String? = null,
    val logoUrl: String? = null,
    val reviewsCount: Int? = null,
    val ratingGoodReview: Float? = null,
    val ratingGoodReviewVoteCount: Int? = null,
    val ratingKinopoisk: Float? = null,
    val ratingKinopoiskVoteCount: Int? = null,
    val ratingImdb: Float? = null,
    val ratingImdbVoteCount: Int? = null,
    val ratingFilmCritics: Float? = null,
    val ratingFilmCriticsVoteCount: Int? = null,
    val ratingAwait: Float? = null,
    val ratingAwaitCount: Int? = null,
    val ratingRfCritics: Float? = null,
    val ratingRfCriticsVoteCount: Int? = null,
    val webUrl: String? = null,
    val year: Int? = null,
    val filmLength: Int? = null,
    val slogan: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val editorAnnotation: String? = null,
    val isTicketsAvailable: Boolean = false,
    val productionStatus: String? = null,
    val type: MovieType? = null,
    val ratingMpaa: String? = null,
    val ratingAgeLimits: String? = null,
    val countries: List<CountryModel>? = null,
    val genres: List<GenreModel>? = null,
    val startYear: Int? = null,
    val endYear: Int? = null,
    val serial: Boolean = false,
    val shortFilm: Boolean = false,
    val completed: Boolean = false,
    val hasImax: Boolean = false,
    val has3D: Boolean = false,
    val lastSync: String? = null
) : java.io.Serializable

@Serializable
data class CountryModel(
    val country: String
) : java.io.Serializable

@Serializable
data class GenreModel(
    val genre: String
) : java.io.Serializable
