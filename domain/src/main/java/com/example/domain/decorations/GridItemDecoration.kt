package com.example.domain.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(
    private val spanCount: Int, // Number of columns in the grid
    private val spacing: Int,   // Spacing between items
    private val includeEdge: Boolean // Whether to include spacing on edges of the grid
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // Get item position
        val column = position % spanCount // Get column index

        // Set left spacing
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
        } else {
            outRect.left = column * spacing / spanCount
        }

        // Set top spacing
        if (position < spanCount) {
            outRect.top = spacing // Add top spacing for the first row
        }

        // Set bottom spacing
        outRect.bottom = spacing // Bottom spacing for all rows

        // Set right spacing
        if (includeEdge) {
            outRect.right = (column + 1) * spacing / spanCount
        } else {
            outRect.right = spacing - (column + 1) * spacing / spanCount
        }
    }
}
