package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable


@Serializable
data class MovieTrailerModel(
    val total: Int? = null,
    val items: List<TrailerItems>? = null
): java.io.Serializable

@Serializable
data class TrailerItems(
    val url: String? = null,
    val name: String? = null,
    val site: String? = null,
): java.io.Serializable