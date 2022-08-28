package com.developer.ivan.beerapp.ui.main.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.ui.main.BeerDetailState
import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.beerapp.ui.mapper.toDomain
import com.developer.ivan.beerapp.ui.mapper.toUi
import com.developer.ivan.beerapp.ui.utils.CoroutineManageContext
import com.developer.ivan.beerapp.ui.utils.CoroutineUtils
import com.developer.ivan.domain.Failure
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

    private lateinit var beerUi: BeerUi


    private var localState = MutableStateFlow<BeerDetailState>(BeerDetailState.Idle)

    val state: StateFlow<BeerDetailState>
        get() = localState

    fun getBeer(id: Int) {

        viewModelScope.launch {
            BeerDetailState.IsLoading.postOn(localState)

            getBeer(GetBeerById.Params(id))
                .map { value ->
                    beerUi = value.toUi()
                    BeerDetailState.ShowItem(beerUi).postOn(localState)
                }.mapLeft {
                    handleFailure(it)
                }
        }
    }

    private suspend fun handleFailure(failure: Failure) {
        localState.value = BeerDetailState.Error(failure)
    }

    fun switchAvailability() {
        viewModelScope.launch {
            beerUi = beerUi.copy(isAvailable = !beerUi.isAvailable)
            BeerDetailState.IsLoading.postOn(localState)

            updateBeer(UpdateBeer.Params(beerUi.toDomain()))
                .map {
                    BeerDetailState.ShowItem(beerUi).postOn(localState)
                }
        }
    }
}
