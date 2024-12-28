package com.example.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.core.base.BaseFragment
import com.example.moviedetails.databinding.SimilarLayoutBinding
import com.example.moviedetails.effect.SimilarMoviesPageEffect
import com.example.moviedetails.state.SimilarMoviesPageState
import com.example.moviedetails.viewmodel.SimilarMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SimilarMoviesFragment(var id: Int?) :
    BaseFragment<SimilarMoviesPageState, SimilarMoviesPageEffect, SimilarLayoutBinding, SimilarMoviesViewModel>() {

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> SimilarLayoutBinding
        get() = SimilarLayoutBinding::inflate

    override fun getViewModelClass(): Class<SimilarMoviesViewModel> =
        SimilarMoviesViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        with(binding) {
        }
    }


}