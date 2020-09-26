package com.app.imagesearch.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
(var layoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager?.childCount?:0
        val totalItemCount = layoutManager?.itemCount?:0
        val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition()?:0

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount-3 && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }
    abstract fun loadMoreItems()
}