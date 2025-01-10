package com.example.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.LikedMovieListAdapter
import com.example.adapter.MyMovieListAdapter
import com.example.core.base.BaseFragment
import com.example.core.tools.NavigationCommand
import com.example.core.utils.RecyclerViewUtils
import com.example.domain.decorations.MarginItemDecoration
import com.example.settings.databinding.FragmentProfileBinding
import com.example.settings.effect.ProfileEffect
import com.example.settings.state.ProfileState
import com.example.settings.viewmodel.ProfileViewModel
import com.example.uikit.extensions.gone
import com.example.uikit.extensions.show
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
    private lateinit var likedMovieListAdapter: LikedMovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        if (!::movieListAdapter.isInitialized) {
            initMyListAdapter()
        }
        if (!::likedMovieListAdapter.isInitialized) {
            initLikedAdapter()
        }
        with(binding) {
            toolbar.setToolbarRightIcon(com.example.uikit.R.drawable.menu_burger)
            toolbar.setToolbarSecondaryRightIcon(com.example.uikit.R.drawable.ic_search_white)
            toolbar.setToolBarRightActionClick {
            }
        }

        observeMovieList()
        observeLikedMovieList()
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getMyMovieList()
        viewmodel.getLikedMovieList()
    }

    private fun observeLikedMovieList() {
        viewmodel.likedList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                likedMovieListAdapter.submitList(list)
                binding.likedMovieListAdapter.show()
            } else {
                binding.likedMovieListAdapter.gone()
            }
        })
    }

    private fun observeMovieList() {
        viewmodel.myList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                movieListAdapter.submitList(list)
                binding.myMovieListAdapter.show()
            } else {
                binding.myMovieListAdapter.gone()
            }
        })
    }

    private fun initLikedAdapter() {
        val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false // Disable vertical scrolling
            }
        }
        likedMovieListAdapter = LikedMovieListAdapter(LikedMovieListAdapter.MovieItemClick {
            viewmodel.navigate(
                NavigationCommand.Deeplink(
                    "com.example://movieDetails/{movieId}",
                    mutableMapOf("movieId" to it.id.toString()),
                    true
                )
            )
        })
        val itemDecoration = MarginItemDecoration(15, 1)
        binding.likedMovieListAdapter.adapter = likedMovieListAdapter
        binding.likedMovieListAdapter.addItemDecoration(itemDecoration)
        binding.likedMovieListAdapter.layoutManager = layoutManager
    }

    private fun initMyListAdapter() {
        val layoutManager = RecyclerViewUtils.layoutWithDisabledVerticalScroll(requireContext())
        movieListAdapter = MyMovieListAdapter(MyMovieListAdapter.MovieItemClick {
            viewmodel.navigate(
                NavigationCommand.Deeplink(
                    "com.example://movieDetails/{movieId}",
                    mutableMapOf("movieId" to it.id.toString()),
                    true
                )
            )
        })
        val itemDecoration = MarginItemDecoration(15, 1)
        binding.myMovieListAdapter.adapter = movieListAdapter
        binding.myMovieListAdapter.addItemDecoration(itemDecoration)
        binding.myMovieListAdapter.layoutManager = layoutManager
    }

}