package com.developer.ivan.beerapp.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LIMIT_SCREENS_TO_END = 8

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = LIMIT_SCREENS_TO_END,
    isLoading: Boolean = false,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            totalItemsNumber > buffer && lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value && !isLoading }
            .distinctUntilChanged()
            .collect {
                if (!isLoading) {
                    onLoadMore()
                }
            }
    }
}
