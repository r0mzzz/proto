package com.example.core.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.example.core.tools.NavigationCommand
import com.example.domain.base.BaseUseCase
import com.example.domain.base.CompletionBlock
import hesab.az.core.tools.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel<State, Effect> : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()
    private val _state = MutableLiveData<State>()
    private val _effect = SingleLiveEvent<Effect>()

    val state: LiveData<State>
        get() = _state

    val effect: SingleLiveEvent<Effect>
        get() = _effect

    protected fun postState(state: State){
        Log.d("StateUpdate", "State is being posted: $state")
        _state.value = state
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


    protected fun <P, R, U: BaseUseCase<P, R>> U.launch(
        param: P,
        block: CompletionBlock<R>
    ){
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)
            val proxy: CompletionBlock<R> = {
                onStart = {
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onTerminate = {
                    actualRequest.onTerminate?.invoke()
                }
                onError = {
                    actualRequest.onError?.invoke(it)
                }
            }
            execute(param, proxy)
        }
    }
}