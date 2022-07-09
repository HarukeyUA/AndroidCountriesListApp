package com.harukeyua.countriesList.features.countriesList.list

import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.harukeyua.countriesList.extensions.dp

class CountriesListItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        when (parent.getChildAdapterPosition(view)) {
            else -> outRect.set(14.dp, 0, 14.dp, 5.dp)
        }
    }
}