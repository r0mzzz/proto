package com.example.core.utils


import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewUtils {

    fun layoutWithDisabledVerticalScroll(
        context: Context,
        orientation: Int = RecyclerView.HORIZONTAL
    ): LinearLayoutManager {
        return object : LinearLayoutManager(context, orientation, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    fun layoutWithDisabledHorizontalScroll(
        context: Context,
        orientation: Int = RecyclerView.HORIZONTAL
    ): LinearLayoutManager {
        return object : LinearLayoutManager(context, orientation, false) {

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    fun layoutWithScrollDisabled(
        context: Context,
        orientation: Int = RecyclerView.HORIZONTAL
    ): LinearLayoutManager {
        return object : LinearLayoutManager(context, orientation, false) {

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }
}
