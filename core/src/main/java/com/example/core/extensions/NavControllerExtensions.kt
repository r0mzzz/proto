package com.example.core.extensions

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.example.core.R

fun NavController.navigateWithAnimation(
    navDirections: NavDirections,
    extras: Navigator.Extras? = null
) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)       // Animation when entering the new destination
        .setExitAnim(R.anim.slide_out_left)        // Animation when leaving the current destination
        .setPopEnterAnim(R.anim.slide_out_left)     // Animation when returning to the previous destination
        .setPopExitAnim(R.anim.slide_in_right)    // Animation when popping the current destination off the back stack
        .build()

    // Navigate using NavController and apply animations
    navigate(navDirections, navOptions)
}


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
            .setPopUpTo(currentId, false)
            .build()
        navigate(deepLink, options)
    } else
        navigate(deepLink)
}

