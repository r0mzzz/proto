package com.example.domain.entity.moviedetails

import com.example.domain.entity.enums.MovieType
import kotlinx.serialization.Serializable

@Serializable
class MovieDetailsModel (
    val title: String? = null,
    val rating: String? = null,
    val quality: Int? = null,
    val description: String? = null,
    val format: MovieType? = null,
): java.io.Serializable