package com.example.domain.entity.models

import kotlinx.serialization.Serializable

@Serializable
data class ViewPagerTabModel(
    val name: String? = null,
    val id: Int? = null,
) : java.io.Serializable