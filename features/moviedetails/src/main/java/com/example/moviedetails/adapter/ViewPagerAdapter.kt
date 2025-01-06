package com.example.moviedetails.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entity.models.ViewPagerTabModel
import com.example.domain.entity.moviedetails.MovieReviewModel
import com.example.domain.entity.moviedetails.SimilarMoviesModel
import com.example.moviedetails.view.SimilarMoviesFragment
import com.example.moviedetails.view.MovieReviewFragment

class ViewPagerAdapter(
    private var data: List<ViewPagerTabModel>,
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

    private fun createDynamicFragment(item: ViewPagerTabModel): Fragment? {
        return when (item.name) {
             "Similar" -> {
                val fragment = SimilarMoviesFragment()
                val bundle = Bundle().apply {
                    item.id?.let { putInt("movieId", it) }
                }
                fragment.arguments = bundle
                fragment.view?.post {
                    fragment.view?.viewTreeObserver?.addOnPreDrawListener {
                        adjustViewPagerHeight(fragment)
                        true
                    }
                }
                fragment
            }

            "Overviews" -> {
                val fragment = MovieReviewFragment(item.id)
                // Set the ViewPager height dynamically after the layout is complete.
                fragment.view?.post {
                    fragment.view?.viewTreeObserver?.addOnPreDrawListener {
                        adjustViewPagerHeight(fragment)
                        true
                    }
                }
                fragment
            }

            else -> null
        }
    }

    // This method adjusts the ViewPager height based on the content inside the RecyclerView.
    private fun adjustViewPagerHeight(fragment: Fragment) {
        val recyclerView =
            fragment.view?.findViewById<RecyclerView>(com.example.moviedetails.R.id.similar_movies_adapter) // Modify with your RecyclerView's ID
        recyclerView?.let {
            val params = fragment.view?.layoutParams
            val height = it.computeVerticalScrollRange()
            params?.height = height
            fragment.view?.layoutParams = params
        }
    }
}
