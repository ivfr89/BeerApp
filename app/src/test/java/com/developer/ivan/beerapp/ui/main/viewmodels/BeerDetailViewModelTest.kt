package com.developer.ivan.beerapp.ui.main.viewmodels

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.developer.ivan.beerapp.model.BeerMother
import com.developer.ivan.beerapp.ui.main.screens.detail.BeerDetailState
import com.developer.ivan.beerapp.ui.main.screens.detail.BeerDetailViewModel
import com.developer.ivan.beerapp.utils.ViewModelTest
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.toRight
import com.developer.ivan.interactors.GetBeerById
import com.developer.ivan.interactors.UpdateBeer
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class BeerDetailViewModelTest : ViewModelTest() {

    private val mother: BeerMother = BeerMother()

    private val mockStateObserver = mockk<Observer<BeerDetailState>>(relaxed = true)
    private val getBeerByIdInteractor =
        mockk<GetBeerById>(relaxed = true)
    private val updateBeerInteractor =
        mockk<UpdateBeer>(relaxed = true)

    private val viewModel: BeerDetailViewModel = BeerDetailViewModel(
        getBeer = getBeerByIdInteractor,
        updateBeer = updateBeerInteractor,
        dispatcher = testDispatcher
    )

    @Before
    fun setupViewModel() {
        viewModel.state.asLiveData().observeForever(mockStateObserver)
    }

    @After
    fun stopObservers() {
        viewModel.state.asLiveData().removeObserver(mockStateObserver)
    }

    @Test
    fun `getBeer method should set Loading state when starts ALWAYS`() {
        val expectedId = mother.givenAnyid()

        givenAnySuccessfullyAvailableResponseOnGetBeer()

        viewModel.getBeer(expectedId)

        verify {
            mockStateObserver.onChanged(ofType(BeerDetailState.Loading::class))
        }
    }

    @Test
    fun `getBeer method should set ShowItem state if beer are fetched successfully`() {
        val expectedId = mother.givenAnyid()

        givenAnySuccessfullyAvailableResponseOnGetBeer()

        viewModel.getBeer(expectedId)

        verify {
            mockStateObserver.onChanged(ofType(BeerDetailState.ShowItem::class))
        }
    }

    @Test
    fun `switchAvailability method set ShowItem state if beer update successfully`() {
        val expectedId = mother.givenAnyid()
        val expectedBeer = mother.givenABeer(isAvailable = false)

        givenAnySuccessfullyAvailableResponseOnGetBeer()

        viewModel.getBeer(expectedId)

        givenAnySuccessResponseOnUpdateBeer(expectedBeer)

        viewModel.switchAvailability()

        verify {
            mockStateObserver.onChanged(ofType(BeerDetailState.ShowItem::class))
        }
    }

    private fun givenAnySuccessfullyAvailableResponseOnGetBeer() {
        coEvery { getBeerByIdInteractor(any()) } returns mother.givenABeer().toRight()
    }

    private fun givenAnySuccessResponseOnUpdateBeer(beer: Beer) {
        coEvery { updateBeerInteractor(UpdateBeer.Params(beer)) } returns
                Unit.toRight()
    }

}
