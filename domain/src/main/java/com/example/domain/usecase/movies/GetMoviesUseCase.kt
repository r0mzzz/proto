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
) : BaseUseCase<Unit, MoviesResponse>(context, convertor) {

    override suspend fun executeOnBackground(params: Unit): MoviesResponse {
        return movieRepository.getMovies()
    }
}
