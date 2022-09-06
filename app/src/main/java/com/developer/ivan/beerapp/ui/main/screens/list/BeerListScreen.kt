package com.developer.ivan.beerapp.ui.main.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.androidbase.components.SmallCard
import com.developer.ivan.beerapp.androidbase.components.SmallCardSkeleton
import com.developer.ivan.beerapp.androidbase.theme.Red_60
import com.developer.ivan.beerapp.androidbase.utils.InfiniteListHandler
import com.developer.ivan.beerapp.androidbase.utils.LazyColumnPaginable
import com.developer.ivan.beerapp.ui.main.models.BeerUi

const val BEER_LIST_TAG = "BeerListTag"

@Composable
fun BeerListScreen(
    viewModel: BeerListViewModel = hiltViewModel(),
    onNavigateToBeerDetail: (id: Int) -> Unit
) {
    var forceReload by rememberSaveable { mutableStateOf(true) }

    viewModel.getBeers(fromStart = forceReload, force = forceReload)

    HandleObserverStates(
        viewModel = viewModel,
        onNavigateToBeerDetail = {
            onNavigateToBeerDetail(it)
            forceReload = false
        }
    )
}

@Composable
fun HandleObserverStates(
    viewModel: BeerListViewModel,
    onNavigateToBeerDetail: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val lazyState = rememberLazyListState()

    InfiniteListHandler(
        listState = lazyState,
        isLoading = state is BeerListState.Paging
    ) {
        viewModel.loadMore()
    }

    when (state) {
        BeerListState.Idle -> Unit
        is BeerListState.Error -> Unit
        BeerListState.Loading,
        is BeerListState.Paging,
        is BeerListState.WithItems -> {
            LazyColumnPaginable<BeerUi>(
                modifier = Modifier.testTag(BEER_LIST_TAG),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                lazyState = lazyState,
                lazyType = mapLazyType(state),
                contentSkeleton = {
                    SmallCardSkeleton(
                        Modifier
                            .padding(8.dp)
                            .height(80.dp)
                    )
                },
                loader = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center),
                            color = MaterialTheme.colors.primaryVariant,
                            strokeWidth = 2.dp
                        )
                    }
                }
            ) {
                SmallCard(
                    Modifier
                        .padding(8.dp, vertical = 4.dp)
                        .height(80.dp),
                    title = it.name,
                    subtitle = it.tagline.orEmpty(),
                    url = it.imageUrl.orEmpty(),
                    color = it.takeIf { it.isAvailable }?.let { MaterialTheme.colors.surface }
                        ?: run { Red_60 },
                    onClick = { onNavigateToBeerDetail(it.id.toInt()) },
                    iconDrawableRes = R.drawable.ic_beer
                )
            }
        }
    }
}
