package com.example.domain.exceptions

import java.util.logging.ErrorManager
import javax.inject.Inject

class ErrorConvertorImpl @Inject constructor(private val mappers: Set<@JvmSuppressWildcards ErrorMapper>) :
    ErrorConvertor {
    override fun convert(throwable: Throwable): Throwable {
        mappers.forEach {
            val error = it.mapError(throwable)
            return error
        }
        return throwable
    }
}

