package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.moviedetails.MovieTrailerModel
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMovieTrailerUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetMovieTrailerUseCase.Params, MovieTrailerModel>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): MovieTrailerModel {
        return movieRepository.getMovieTrailer(
            params.id,
        )
    }

    class Params(
        val id: String,
    )

}
