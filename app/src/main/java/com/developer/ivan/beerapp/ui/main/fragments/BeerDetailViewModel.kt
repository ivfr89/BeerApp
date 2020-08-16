package com.developer.ivan.beerapp.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.di.PerFragment
import com.developer.ivan.beerapp.ui.main.UIBeer
import com.developer.ivan.beerapp.ui.mapper.UIMapper
import com.developer.ivan.domain.Failure
import com.developer.ivan.usecases.GetBeerById
import com.developer.ivan.usecases.UpdateBeer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("RedundantSuspendModifier")
@PerFragment
class BeerDetailViewModel @Inject constructor(
    private val getBeer: GetBeerById,
    private val updateBeer: UpdateBeer,
    private val uiMapper: UIMapper
) : ViewModel() {

    lateinit var uiBeer: UIBeer

    sealed class BeerListState {
        class Error(val failure: Failure) : BeerListState()
        class ShowItem(val item: UIBeer) : BeerListState()
        class IsLoading(val isLoading: Boolean) : BeerListState()
    }

    private var _beersStateData = MutableLiveData<BeerListState>()

    val beersStateData: LiveData<BeerListState>
        get() = _beersStateData

    fun getBeer(id: Int) {

        viewModelScope.launch {
            getBeer(GetBeerById.Params(id))
                .onStart { _beersStateData.value = BeerListState.IsLoading(true) }
                .collect { value ->
                    _beersStateData.value = BeerListState.IsLoading(false)

                    value.fold(::handleFailure) { beer ->
                        also {
                            uiBeer = uiMapper.convertBeerToUIBeer(beer)
                            _beersStateData.value = BeerListState.ShowItem(uiBeer)
                        }
                    }
                }
        }

    }

    private suspend fun handleFailure(failure: Failure) {
        _beersStateData.value = BeerListState.Error(failure)
    }

    fun switchAvailability() {

        viewModelScope.launch {
            val updatedUIBeer = uiBeer.copy(isAvailable = !uiBeer.isAvailable)
            updateBeer(UpdateBeer.Params(uiMapper.convertUIBeerToDomain(updatedUIBeer)))
                .onStart { _beersStateData.value = BeerListState.IsLoading(true) }
                .collect {
                    _beersStateData.value = BeerListState.IsLoading(false)
                }
        }



    }

}