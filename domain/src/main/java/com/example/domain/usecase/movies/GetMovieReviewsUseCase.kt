package com.example.domain.usecase.movies

import com.example.domain.base.BaseUseCase
import com.example.domain.entity.moviedetails.MovieReviewModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMovieReviewsUseCase @Inject constructor(
    context: CoroutineContext,
    convertor: ErrorConvertor,
    private val movieRepository: MovieRepository
) : BaseUseCase<GetMovieReviewsUseCase.Params, List<MovieReviewModel>>(context, convertor) {

    override suspend fun executeOnBackground(params: Params): List<MovieReviewModel> {
        return movieRepository.getMovieReviews(
            params.id,
        )
    }

    class Params(
        val id: String,
    )

}
