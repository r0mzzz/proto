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
        .setEnterAnim(com.example.uikit.R.anim.enter_from_right)
        .setExitAnim(com.example.uikit.R.anim.exit_to_left)
        .setPopEnterAnim(com.example.uikit.R.anim.enter_from_left)
        .setPopExitAnim(com.example.uikit.R.anim.exit_to_right)
        .build()
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
        val navOptions = NavOptions.Builder()
            .setEnterAnim(com.example.uikit.R.anim.enter_from_right)
            .setExitAnim(com.example.uikit.R.anim.exit_to_left)
            .setPopEnterAnim(com.example.uikit.R.anim.enter_from_left)
            .setPopExitAnim(com.example.uikit.R.anim.exit_to_right)
            .setPopUpTo(currentId, false)
            .build()
        val options = NavOptions.Builder()
            .setPopUpTo(currentId, false)
            .build()
        navigate(deepLink, navOptions)
    } else
        navigate(deepLink)
}

