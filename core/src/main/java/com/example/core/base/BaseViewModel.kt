package com.example.core.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.example.core.tools.NavigationCommand
import hesab.az.core.tools.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()


    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }
}