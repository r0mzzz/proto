package com.example.proto.viewmodel

import com.example.core.base.BaseViewModel
import com.example.proto.effect.MainPageEffect
import com.example.proto.state.MainPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel<MainPageState, MainPageEffect>() {


}