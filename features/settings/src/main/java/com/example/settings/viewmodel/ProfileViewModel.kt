package com.example.settings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.domain.database.MyListRepository
import com.example.domain.database.entity.LikedMovies
import com.example.domain.database.entity.MyList
import com.example.settings.effect.ProfileEffect
import com.example.settings.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val myListRepository: MyListRepository
) : BaseViewModel<ProfileState, ProfileEffect>() {

    var myList = MutableLiveData<List<MyList>>(emptyList())
    var likedList = MutableLiveData<List<LikedMovies>>(emptyList())


    fun getMyMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = myListRepository.getAllList()
            myList.postValue(items)
        }
    }

    fun getLikedMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val liked = myListRepository.getAllLiked()
            likedList.postValue(liked)
        }
    }
}