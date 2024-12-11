package com.example.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.base.BaseFragment
import com.example.settings.databinding.FragmentSettingsBinding
import com.example.settings.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {
    override fun getViewModelClass(): Class<SettingsViewModel> = SettingsViewModel::class.java
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}