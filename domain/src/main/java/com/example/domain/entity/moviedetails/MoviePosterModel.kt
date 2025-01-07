package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable

@Serializable
data class MoviePosterModel(
    val total: Int? = null,
    val totalPages: Int? = null,
    val items: List<Poster>
) : java.io.Serializable

@Serializable
data class Poster(
    val imageUrl: String? = null,
    val previewUrl: String? = null,
) : java.io.Serializable

