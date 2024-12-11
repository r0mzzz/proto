package com.example.proto

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.core.base.BaseFragment
import com.example.proto.databinding.FragmentHomePageBinding

class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate


    override val bindViews: FragmentHomePageBinding.() -> Unit =
        {}


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
                findNavController().navigate(Uri.parse("com.example://settings"))
            }
        }
    }

}