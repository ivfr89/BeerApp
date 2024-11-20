package com.developer.ivan.ui.main.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.androidbase.utils.CoroutineManageContext
import com.developer.ivan.beerapp.androidbase.utils.CoroutineUtils
import com.developer.ivan.domain.Failure
import com.developer.ivan.interactors.GetBeers
import com.developer.ivan.ui.main.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    fun getBeers(fromStart: Boolean = true, force: Boolean = true) {
        viewModelScope.launch(dispatcher) {
            if (fromStart) {
                BeerListState.Loading.postOn(localState)
            }
            getBeers(GetBeers.Params(force))
                .map { listBeers ->
                    BeerListState.WithItems(listBeers.toUi()).postOn(localState)
                }.mapLeft {
                    handleFailure(it)
                }
        }
    }

    fun loadMore() {
        viewModelScope.launch(dispatcher) {
            when (val currentState = state.value) {
                BeerListState.Idle,
                is BeerListState.Error,
                BeerListState.Loading,
                is BeerListState.Paging -> Unit
                is BeerListState.WithItems -> {
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
