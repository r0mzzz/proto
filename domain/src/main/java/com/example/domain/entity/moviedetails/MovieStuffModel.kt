package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable


@Serializable
data class MovieStuffModel(
    val staffId: Int? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val description: String? = null,
    val posterUrl: String? = null,
    val professionText: String? = null,
    val professionKey: String? = null,
): java.io.Serializable