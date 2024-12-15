package com.example.domain.exceptions

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}