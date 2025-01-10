package com.example.settings.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.MyMovieListAdapter
import com.example.core.base.BaseFragment
import com.example.domain.decorations.MarginItemDecoration
import com.example.settings.databinding.FragmentProfileBinding
import com.example.settings.effect.ProfileEffect
import com.example.settings.state.ProfileState
import com.example.settings.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Profile :
    BaseFragment<ProfileState, ProfileEffect, FragmentProfileBinding, ProfileViewModel>() {
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun getViewModelClass(): Class<ProfileViewModel> =
        ProfileViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var movieListAdapter: MyMovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovieList()
        initViews()
    }

    private fun initViews() {
        initAdapter()
        with(binding) {
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getMyMovieList()
    }

    private fun observeMovieList() {
        viewmodel.myList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                movieListAdapter.submitList(list)
            } else {
                Log.i("MovieDetails", "The list is empty.")
            }
        })
    }

    private fun initAdapter() {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        movieListAdapter = MyMovieListAdapter(MyMovieListAdapter.MovieItemClick {

        })
        val itemDecoration = MarginItemDecoration(15, 1)
        binding.myMovieListAdapter.adapter = movieListAdapter
        binding.myMovieListAdapter.addItemDecoration(itemDecoration)
        binding.myMovieListAdapter.layoutManager = layoutManager
    }

}