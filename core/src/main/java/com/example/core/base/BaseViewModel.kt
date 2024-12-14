package com.example.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.example.core.tools.NavigationCommand
import hesab.az.core.tools.SingleLiveEvent

open class BaseViewModel<State, Effect> : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()
    private val _state = MutableLiveData<State>()
    private val _effect = SingleLiveEvent<Effect>()

    val state: LiveData<State>
        get() = _state

    val effect: SingleLiveEvent<Effect>
        get() = _effect

    protected fun postState(state: State){
        _state.postValue(state)
    }

    protected fun postEffect(effect: Effect){
        _effect.postValue(effect)
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }
}