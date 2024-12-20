package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMoviesUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetMoviesUseCase.Params, MoviesResponse>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): MoviesResponse {
        return movieRepository.getMovies(
            params.type,
            params.yearFrom,
            params.yearTo,
            params.ratingFrom,
            params.ratingTo
        )
    }

    class Params(
        val type: MovieType? = null,
        val yearFrom: String? = null,
        val yearTo: String? = null,
        val ratingFrom: String? = null,
        val ratingTo: String? = null
    )

}
