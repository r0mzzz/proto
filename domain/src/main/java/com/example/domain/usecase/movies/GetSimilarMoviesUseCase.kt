package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.enums.MovieType
import com.example.domain.entity.home.MoviesResponse
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.entity.moviedetails.SimilarMovieModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSimilarMoviesUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetSimilarMoviesUseCase.Params, SimilarMoviesModel>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): SimilarMoviesModel {
        return movieRepository.getSimilarMovies(
            params.id,
        )
    }

    class Params(
        val id: String,
    )

}
