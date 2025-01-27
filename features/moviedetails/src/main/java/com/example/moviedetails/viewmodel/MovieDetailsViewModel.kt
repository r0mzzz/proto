package com.example.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.domain.database.MyListRepository
import com.example.domain.database.entity.LikedMovies
import com.example.domain.database.entity.MyList
import com.example.domain.entity.moviedetails.MovieDetailsModel
import com.example.domain.usecase.movies.GetMovieDetailsUseCase
import com.example.domain.usecase.movies.GetMovieStuffUseCase
import com.example.domain.usecase.movies.GetMovieTrailerUseCase
import com.example.moviedetails.effect.MovieDetailsPageEffect
import com.example.moviedetails.state.MovieDetailsPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieStuffUseCase: GetMovieStuffUseCase,
    private val myListRepository: MyListRepository

) : BaseViewModel<MovieDetailsPageState, MovieDetailsPageEffect>() {
    var movieDetails = MutableLiveData<MovieDetailsModel>(null)
    var currentMovieId = MutableLiveData("")
    var previousMovieId = MutableLiveData("")
    var myList = MutableLiveData<List<MyList>>(emptyList())

    fun getMovieDetail(id: String) {
        getMovieDetailUseCase.launchNoLoading(GetMovieDetailsUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieDetailSuccess(it))
            }
        }
    }

    fun getMovieTrailer(id: String) {
        getMovieTrailerUseCase.launchNoLoading(GetMovieTrailerUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieTrailerSuccess(it))
            }
        }
    }

    fun getMovieStuff(id: String) {
        getMovieStuffUseCase.launchNoLoading(GetMovieStuffUseCase.Params(id)) {
            onSuccess = {
                postState(MovieDetailsPageState.GetMovieStuffSuccess(it))
            }
        }
    }

    fun dropTable() {
        viewModelScope.launch(Dispatchers.IO) {
            myListRepository.deleteAll()
        }
    }

    fun addItem(item: MyList) {
        viewModelScope.launch(Dispatchers.IO) {
            myListRepository.insertNewData(item)
        }
    }

    fun likeMovie(item: LikedMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            myListRepository.insertDataToLiked(item)
        }
    }
}