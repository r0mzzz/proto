package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMovieDetailsUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetMovieDetailsUseCase.Params, MovieDetailsModel>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): MovieDetailsModel {
        return movieRepository.getMovieDetails(
            params.id,
        )
    }

    class Params(
        val id: String,
    )

}
