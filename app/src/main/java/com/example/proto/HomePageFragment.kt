package com.example.proto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.proto.databinding.FragmentHomePageBinding
import com.example.proto.viewmodel.MainViewModel

class HomePageFragment : BaseFragment<FragmentHomePageBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        binding.apply {
            text.text = "6666"
            navBtn.setOnClickListener {
                viewmodel.navigate(
                    NavigationCommand.Deeplink(
                        "com.example://settings",
                        null,
                        false
                    )
                )
            }
        }
    }

}