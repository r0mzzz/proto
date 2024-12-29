package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewModel(
    val id: Int? = null,
    val name: String? = null
) : java.io.Serializable