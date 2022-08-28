package com.developer.ivan.beerapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val DEFAULT_SKELETON_ITEMS = 20

@Composable
fun <T : UniqueItem> LazyColumnPaginable(
    modifier: Modifier = Modifier,
    lazyState: LazyListState,
    lazyType: LazyType,
    verticalArrangement : Arrangement.Vertical,
    contentSkeleton: (@Composable () -> Unit)? = null,
    loader: (@Composable () -> Unit)? = null,
    content: (@Composable (T) -> Unit)? = null
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        modifier = modifier,
        state = lazyState,
        userScrollEnabled = lazyType !is LazyType.Loading
    ) {
        lazyColumnContent(
            lazyType = lazyType,
            contentSkeleton = contentSkeleton,
            loader = loader,
            content = content
        )
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : UniqueItem> LazyListScope.lazyColumnContent(
    lazyType: LazyType,
    contentSkeleton: (@Composable () -> Unit)? = null,
    loader: (@Composable () -> Unit)? = null,
    content: (@Composable (T) -> Unit)? = null
) {
    when (lazyType) {
        is LazyType.Render<*> -> {
            (lazyType as? LazyType.Render<T>)?.let {
                items(it.renderItem, key = { it.id }) { item ->
                    content?.invoke(item)
                }
            }
        }
        is LazyType.Loading -> {
            items(lazyType.total) {
                contentSkeleton?.invoke()
            }
        }
        is LazyType.Paging<*> -> {
            (lazyType as? LazyType.Paging<T>)?.let {
                items(it.renderItem) { item ->
                    content?.invoke(item)
                }
                item {
                    loader?.invoke()
                }
            }
        }
        LazyType.None -> Unit
    }
}

sealed class LazyType {
    class Render<T>(val renderItem: List<T>) : LazyType()
    class Paging<T>(val renderItem: List<T>) : LazyType()
    class Loading(val total: Int = DEFAULT_SKELETON_ITEMS) : LazyType()
    object None : LazyType()
}
