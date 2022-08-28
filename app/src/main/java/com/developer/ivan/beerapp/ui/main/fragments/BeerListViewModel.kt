package com.developer.ivan.beerapp.ui.main.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.ui.main.BeerListState
import com.developer.ivan.beerapp.ui.mapper.toUi
import com.developer.ivan.beerapp.ui.utils.CoroutineManageContext
import com.developer.ivan.beerapp.ui.utils.CoroutineUtils
import com.developer.ivan.domain.Failure
import com.developer.ivan.interactors.GetBeers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("RedundantSuspendModifier")
@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeers: GetBeers,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), CoroutineUtils by CoroutineManageContext(dispatcher) {

    private val localState: MutableStateFlow<BeerListState> =
        MutableStateFlow(BeerListState.Idle)
    val state: StateFlow<BeerListState> = localState

    fun getBeers(fromStart: Boolean = true) {

        viewModelScope.launch {
            if (fromStart)
                BeerListState.IsLoading.postOn(localState)
            getBeers(Unit)
                .map { listBeers ->
                    BeerListState.ShowItems(listBeers.toUi()).postOn(localState)
                }.mapLeft {
                    handleFailure(it)
                }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            when (val currentState = state.value) {
                BeerListState.Idle,
                is BeerListState.Error,
                BeerListState.IsLoading,
                is BeerListState.Paging -> Unit
                is BeerListState.ShowItems -> {
                    BeerListState.Paging(
                        currentState.beerList
                    ).postOn(localState)
                    getBeers(false)
                }
            }
        }
    }

    private suspend fun handleFailure(failure: Failure) {
        BeerListState.Error(
            failure
        ).postOn(localState)
    }
}
