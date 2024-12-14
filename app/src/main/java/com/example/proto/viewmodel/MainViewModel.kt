package com.example.proto.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.proto.network.MovieRepository
import com.example.proto.network.MovieService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {


    fun getMovies() = viewModelScope.launch {
        val response = movieRepository.getMovies()
        Log.d("response", response.body().toString())
    }

}