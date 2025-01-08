package com.example.moviedetails.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entity.enums.SegmentTabs
import com.example.domain.entity.models.ViewPagerTabModel
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
        return when (item.type) {
            SegmentTabs.SIMILAR.name -> {
                val fragment = SimilarMoviesFragment()
                val bundle = Bundle().apply {
                    item.id?.let { putString("movieId", it) }
                    item.recyclerViewId?.let { putInt("recyclerViewId", it) }

                }
                fragment.arguments = bundle
                fragment.view?.viewTreeObserver?.addOnPreDrawListener {
                    fragment.view?.post {
                        adjustViewPagerHeight(fragment, item.recyclerViewId)
                    }
                    true
                }

                fragment
            }

            SegmentTabs.POSTERS.name -> {
                val fragment = MovieReviewFragment()
                val bundle = Bundle().apply {
                    item.id?.let { putString("movieId", it) }
                    item.recyclerViewId?.let {
                        putInt(
                            "recyclerViewId",
                            it
                        )
                    }
                }
                fragment.arguments = bundle

                fragment.view?.viewTreeObserver?.addOnPreDrawListener {
                    fragment.view?.post {
                        adjustViewPagerHeight(
                            fragment,
                            item.recyclerViewId
                        )
                    }
                    true
                }

                fragment
            }

            else -> null
        }
    }


    fun adjustViewPagerHeight(fragment: Fragment, recyclerViewId: Int?) {
        recyclerViewId?.let { id ->
            val recyclerView =
                fragment.view?.findViewById<RecyclerView>(id) // Use dynamic RecyclerView ID
            recyclerView?.let {
                val params = fragment.view?.layoutParams
                val height = it.computeVerticalScrollRange()
                params?.height = height
                fragment.view?.layoutParams = params
            }
        }
    }
}
