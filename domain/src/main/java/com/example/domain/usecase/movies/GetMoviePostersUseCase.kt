package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.moviedetails.MoviePosterModel
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMoviePostersUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetMoviePostersUseCase.Params, MoviePosterModel>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): MoviePosterModel {
        return movieRepository.getMoviePosters(
            params.id,
            params.type,
            params.page,
        )
    }

    class Params(
        val id: String,
        val type: String,
        val page: String,
    )

}
