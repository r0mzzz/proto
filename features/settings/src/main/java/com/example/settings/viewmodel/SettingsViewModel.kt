package com.example.settings.viewmodel

import com.example.core.base.BaseViewModel
import com.example.settings.effect.SettingsPageEffect
import com.example.settings.state.SettingsPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsPageState, SettingsPageEffect>() {
}