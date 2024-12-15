package com.example.domain.exceptions

fun interface ErrorConvertor {
    fun convert(throwable: Throwable): Throwable
}