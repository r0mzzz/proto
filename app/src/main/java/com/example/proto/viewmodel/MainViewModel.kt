package com.example.proto.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.proto.effect.MainPageEffect
import com.example.proto.network.MovieRepository
import com.example.proto.state.MainPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel<MainPageState, MainPageEffect>() {


    fun getMovies() = viewModelScope.launch {
        val response = movieRepository.getMovies()
    }

}