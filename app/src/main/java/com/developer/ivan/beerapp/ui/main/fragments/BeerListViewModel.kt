package com.developer.ivan.beerapp.ui.main.fragments

import android.os.Parcelable
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.ivan.beerapp.core.Event
import com.developer.ivan.beerapp.di.PerFragment
import com.developer.ivan.beerapp.ui.main.models.UIBeer
import com.developer.ivan.beerapp.ui.mapper.UIMapper
import com.developer.ivan.domain.Failure
import com.developer.ivan.usecases.GetBeers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("RedundantSuspendModifier")
@PerFragment
class BeerListViewModel @Inject constructor(
    private val getBeers: GetBeers,
    private val uiMapper: UIMapper,
    var state: Parcelable?,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    sealed class BeerListState {
        class Error(val failure: Failure) : BeerListState()
        class ShowItems(val beerList: List<UIBeer>) : BeerListState()
        class IsLastPage(val isLastPage: Boolean) : BeerListState()
        class IsLoading(val isLoading: Boolean) : BeerListState()
    }

    sealed class NavigationEvent {
        class ToDetail(val arg: UIBeer, val title: TextView) : NavigationEvent()
    }

    private var _beersStateListData = MutableLiveData<BeerListState>()

    val beersStateListData: LiveData<BeerListState>
        get() = _beersStateListData


//    Events

    private val _navigation = MutableLiveData<Event<NavigationEvent>>()
    val navigation: LiveData<Event<NavigationEvent>>
        get() = _navigation


    fun navigateTo(navEvent: NavigationEvent) {
        _navigation.value = Event(navEvent)
    }

    fun getBeers(force: Boolean = false) {

        _beersStateListData.value = BeerListState.IsLoading(true)

        viewModelScope.launch(dispatcher) {
            getBeers(GetBeers.Params(force))
                .collect { value ->
                    _beersStateListData.value =
                        BeerListState.IsLoading(false)

                    value.fold(::handleFailure) { listBeers ->
                        _beersStateListData.value =
                            BeerListState.IsLastPage(listBeers.isEmpty())
                        also {
                            _beersStateListData.value =
                                BeerListState.ShowItems(uiMapper.convertBeersToUIBeers(listBeers))
                        }
                    }
                }
        }

    }

    private suspend fun handleFailure(failure: Failure) {
        _beersStateListData.value =
            BeerListState.Error(
                failure
            )
    }

}