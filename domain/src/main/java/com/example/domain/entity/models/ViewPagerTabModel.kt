package com.example.domain.entity.models

import kotlinx.serialization.Serializable

@Serializable
data class ViewPagerTabModel(
    val id: String? = null,
    val name: String? = null,
    val recyclerViewId: Int? = null,
    val type: String? = null,
) : java.io.Serializable