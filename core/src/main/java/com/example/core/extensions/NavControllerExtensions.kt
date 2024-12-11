package com.example.core.extensions

import android.util.Log
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions


fun NavController.deeplinkNavigate(
    direction: String,
    extras: MutableMap<String, String>? = null,
    isInclusive: Boolean = false
) {
    var deeplinkDirection = direction
    extras?.forEach { (key, value) ->
        deeplinkDirection = deeplinkDirection.replace("{$key}", value)
    }
    val deepLink = NavDeepLinkRequest.Builder
        .fromUri(deeplinkDirection.toUri())
        .build()

    val currentId = currentDestination?.id
    if (currentId != null && isInclusive) {
        val options = NavOptions.Builder()
            .setPopUpTo(currentId, true)
            .build()
        navigate(deepLink, options)
    } else
        navigate(deepLink)
}

