package com.example.core.tools

import androidx.navigation.NavDirections
import androidx.navigation.Navigator

sealed class NavigationCommand {
    data class To(val directions: NavDirections, val extras: Navigator.Extras? = null) :
        NavigationCommand()

    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    data class Deeplink(
        val deeplink: String,
        val extras: MutableMap<String, String>? = null,
        val isInclusive: Boolean = false
    ) : NavigationCommand()
}