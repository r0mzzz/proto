package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
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
            params.ratingFrom,
            params.yearTo,
            params.yearFrom,
            params.yearTo
        )
    }

    class Params(
        val type: String? = null,
        val yearTo: String? = null,
        val yearFrom: String? = null,
        val ratingFrom: String? = null,
        val ratingTo: String? = null
    )

}
