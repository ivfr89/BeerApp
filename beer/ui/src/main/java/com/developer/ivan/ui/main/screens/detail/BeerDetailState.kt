package com.developer.ivan.ui.main.screens.detail

import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.domain.Failure

sealed class BeerDetailState {
    object Idle : BeerDetailState()
    class Error(val failure: Failure) : BeerDetailState()
    class ShowItem(val item: BeerUi) : BeerDetailState()
    object Loading : BeerDetailState()
}
