package com.example.domain.entity.moviedetails

import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewModel(
    val total: Int? = null,
    val totalPages: Int? = null,
    val totalPositiveReviews: Int? = null,
    val totalNegativeReviews: Int? = null,
    val totalNeutralReviews: Int? = null,
    val items: List<Review>
) : java.io.Serializable

@Serializable
data class Review(
    val kinopoiskId: Int? = null,
    val type: String? = null,
    val date: String? = null,
    val positiveRating: Int? = null,
    val negativeRating: Int? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
) : java.io.Serializable

