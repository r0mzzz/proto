package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable

@Serializable
data class SimilarMoviesModel(
    val name: String? = null,
    val id: Int? = null,
    val total: Int? = null,
    val items: List<SimilarMovieModel>? = null
) : java.io.Serializable

@Serializable
data class SimilarMovieModel(
    val filmId: Int? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val relationType: String? = null,
) : java.io.Serializable
