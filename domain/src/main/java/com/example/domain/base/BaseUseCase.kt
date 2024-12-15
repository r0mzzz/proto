package com.example.domain.base

import androidx.lifecycle.LiveData
import com.example.domain.exceptions.ErrorConvertor
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = BaseUseCase.Request<T>.() -> Unit

abstract class BaseUseCase<P, R>(
    private val executionContext: CoroutineContext,
    private val errorConvertor: ErrorConvertor
) {

    protected abstract suspend fun executeOnBackground(params: P): R

    suspend fun execute(params: P, block: CompletionBlock<R> = {}) {
        val request = Request<R>().apply(block).also { it.onStart?.invoke() }
        try {
            val result = withContext(executionContext) { executeOnBackground(params) }
            request.onSuccess(result)
        } catch (e: Throwable) {
            request.onError?.invoke(errorConvertor.convert(e))
        } finally {
            request.onTerminate?.invoke()
        }
    }

    suspend fun executeOrThrow(params: P) {
        return withContext(executionContext) {
            try {
                executeOnBackground(params)
            } catch (e: Throwable) {
                throw errorConvertor.convert(e)
            }
        }
    }

    class Request<T> {
        var onSuccess: (T) -> Unit = {}
        var onStart: (() -> Unit)? = null
        var onTerminate: (() -> Unit)? = null
        var onError: ((Throwable) -> Unit)? = null
    }
}