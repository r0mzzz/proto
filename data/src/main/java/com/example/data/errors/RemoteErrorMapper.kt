package com.example.data.errors

import com.example.domain.exceptions.ErrorMapper
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteErrorMapper @Inject constructor() : ErrorMapper {
    override fun mapError(e: Throwable): Throwable = when (e) {
        is HttpException -> mapHttpErrors(e)
        is SocketException,
        is NullPointerException,
        is SocketTimeoutException,
        is UnknownHostException, -> UnknownError(e.toString())
        else -> UnknownError(e.toString())
    }

    private fun mapHttpErrors(error: HttpException): Throwable {
        return error
    }
}