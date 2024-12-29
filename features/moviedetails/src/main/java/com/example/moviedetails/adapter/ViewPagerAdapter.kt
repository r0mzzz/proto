package com.example.moviedetails.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entity.moviedetails.MovieReviewModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.moviedetails.view.SimilarMoviesFragment
import com.example.moviedetails.view.MovieReviewFragment

class ViewPagerAdapter(
    private var data: List<Any>,
    fm: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        val item = data[position]
        return createDynamicFragment(item) ?: Fragment()
    }

    private fun createDynamicFragment(item: Any): Fragment? {
        return when (item) {
            is SimilarMoviesModel -> {
                val fragment = SimilarMoviesFragment()
                val bundle = Bundle().apply {
                    item.id?.let { putInt("movieId", it) }
                }
                fragment.arguments = bundle
                fragment
            }

            is MovieReviewModel -> {
                MovieReviewFragment(item.id)
            }

            else -> null
        }
    }
}
