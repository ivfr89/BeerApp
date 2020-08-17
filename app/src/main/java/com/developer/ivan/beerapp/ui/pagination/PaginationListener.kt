package com.developer.ivan.beerapp.ui.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.ivan.domain.Constants

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    val visibleItemCount = layoutManager.childCount
    val totalItemCount = layoutManager.itemCount
    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
    if (!isLoading && !isLastPage) {
      if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
        && totalItemCount >= Constants.Server.DEFAULT_SIZE) {
        loadMoreItems()
      }
    }
  }

  protected abstract fun loadMoreItems()
  abstract var isLastPage: Boolean
  abstract var isLoading: Boolean

}

class BeerPaginationListener(layoutManager: LinearLayoutManager,
                             override var isLastPage: Boolean,
                             override var isLoading: Boolean,
                             val callback: () -> Unit
) : PaginationListener(layoutManager){
  override fun loadMoreItems() {
    callback()
  }

}