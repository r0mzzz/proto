package com.example.moviedetails.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.moviedetails.view.SimilarMoviesFragment

class ViewPagerAdapter(var data: List<SimilarMoviesModel>, fm: FragmentManager,lifecycle: Lifecycle, ) : FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return SimilarMoviesFragment(data[position].id)
    }
}