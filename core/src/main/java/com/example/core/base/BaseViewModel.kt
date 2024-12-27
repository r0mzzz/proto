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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class BaseViewModel<State, Effect> : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    //    private val _commonEffect = SingleLiveEvent(BaseEffect)
//    val commonEffect: LiveData<Boolean>
//        get() = _commonEffect    val commonEffect: LiveData<Boolean>
//        get() = _commonEffect
    val navigationCommands = SingleLiveEvent<NavigationCommand>()
    private val _state = MutableLiveData<State>()
    private val _effect = SingleLiveEvent<Effect>()

    val state: LiveData<State>
        get() = _state

    val effect: SingleLiveEvent<Effect>
        get() = _effect

    protected fun postState(state: State) {
        Log.d("StateUpdate", "State is being posted: $state")
        _state.value = state
    }

    protected fun handleError(t: Throwable) {
        Log.e("ERROR", t.message.toString())
    }

    protected fun handleLoading(state: Boolean) {
        _isLoading.postValue(state)
    }

    protected fun <T> Flow<T>.handleError(): Flow<T> = catch { handleError(it) }

    protected fun <T> Flow<T>.handleLoading(loadingHandle: (Boolean) -> Unit = ::handleLoading): Flow<T> =
        flow {
            this@handleLoading
                .onStart { loadingHandle(true) }
                .onCompletion { loadingHandle(false) }
                .collect { value ->
                    loadingHandle(false)
                    emit(value)
                }
        }

    protected fun <T> Flow<T>.launchNoLoading(scope: CoroutineScope = viewModelScope): Job =
        this.handleError()
            .launchIn(scope)

    protected fun <T> Flow<T>.launch(
        scope: CoroutineScope = viewModelScope,
        loadingHandle: (Boolean) -> Unit = ::handleLoading
    ): Job = this.handleError()
        .handleError()
        .handleLoading(loadingHandle)
        .launchIn(scope)

    protected fun postEffect(effect: Effect) {
        _effect.postValue(effect)
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }


    protected fun <P, R, U : BaseUseCase<P, R>> U.launchNoLoading(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)
            val proxy: CompletionBlock<R> = {
                onStart = actualRequest.onStart
                onSuccess = actualRequest.onSuccess
                onTerminate = actualRequest.onTerminate
                onError = {
                    actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> U.launch(
        param: P,
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        block: CompletionBlock<R>
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)
            val proxy: CompletionBlock<R> = {
                onStart = {
                    loadingHandle(true)
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onTerminate = {
                    actualRequest.onTerminate?.invoke()
                    loadingHandle(false)
                }
                onError = {
                    actualRequest.onError?.invoke(it)
                }
            }
            execute(param, proxy)
        }
    }
}