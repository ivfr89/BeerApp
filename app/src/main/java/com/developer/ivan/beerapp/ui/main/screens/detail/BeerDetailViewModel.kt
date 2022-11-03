package com.developer.ivan.beerapp.ui.main.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.androidbase.utils.CoroutineManageContext
import com.developer.ivan.beerapp.androidbase.utils.CoroutineUtils
import com.developer.ivan.beerapp.ui.mapper.toUi
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.flatMap
import com.developer.ivan.interactors.GetBeerById
import com.developer.ivan.interactors.UpdateBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("RedundantSuspendModifier")
@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val getBeer: GetBeerById,
    private val updateBeer: UpdateBeer,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), CoroutineUtils by CoroutineManageContext(dispatcher) {

    private var localState = MutableStateFlow<BeerDetailState>(BeerDetailState.Idle)
    val state: StateFlow<BeerDetailState>
        get() = localState

    fun getBeer(id: Int) {
        viewModelScope.launch(dispatcher) {
            BeerDetailState.Loading.postOn(localState)

            getBeer(GetBeerById.Params(id))
                .map { beer ->
                    BeerDetailState.ShowItem(beer.toUi()).postOn(localState)
                }.mapLeft {
                    handleFailure(it)
                }
        }
    }

    private suspend fun handleFailure(failure: Failure) {
        viewModelScope.launchAndPost(localState, dispatcher) {
            BeerDetailState.Error(failure)
        }
    }

    fun switchAvailability(id: Int) {
        viewModelScope.launch(dispatcher) {
            BeerDetailState.Loading.postOn(localState)

            getBeer(GetBeerById.Params(id))
                .flatMap { beer ->
                    updateBeer(UpdateBeer.Params(beer.copy(isAvailable = !beer.isAvailable)))
                        .map { updatedBeer ->
                            BeerDetailState.ShowItem(updatedBeer.toUi()).postOn(localState)
                        }
                }
        }
    }
}
