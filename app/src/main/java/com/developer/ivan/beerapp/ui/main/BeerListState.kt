package com.developer.ivan.beerapp.ui.main

import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.beerapp.ui.utils.LazyType
import com.developer.ivan.domain.Failure

private const val NUMBER_ITEMS_LOADING = 10

sealed class BeerListState {
        object Idle : BeerListState()
        data class Error(val failure: Failure) : BeerListState()
        data class ShowItems(val beerList: List<BeerUi>) : BeerListState()
        object IsLoading : BeerListState()
        data class Paging(val beerList: List<BeerUi>) : BeerListState()
    }

internal fun mapLazyType(state: BeerListState) =
    when(state) {
        is BeerListState.Error,
        BeerListState.Idle -> LazyType.None
        is BeerListState.Paging -> LazyType.Paging(state.beerList)
        BeerListState.IsLoading -> LazyType.Loading(NUMBER_ITEMS_LOADING)
        is BeerListState.ShowItems -> LazyType.Render(state.beerList)
    }
