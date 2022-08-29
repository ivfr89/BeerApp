package com.developer.ivan.beerapp.ui.main.viewmodels

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.developer.ivan.beerapp.model.BeerMother
import com.developer.ivan.beerapp.ui.main.BeerListState
import com.developer.ivan.beerapp.ui.main.screens.viewmodels.BeerListViewModel
import com.developer.ivan.beerapp.utils.ViewModelTest
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.toLeft
import com.developer.ivan.domain.toRight
import com.developer.ivan.interactors.GetBeers
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class BeerListViewModelTest : ViewModelTest() {

    private val mother: BeerMother = BeerMother()

    private val mockStateObserver = mockk<Observer<BeerListState>>(relaxed = true)
    private val getBeersInteractor =
        mockk<GetBeers>(relaxed = true)

    private val viewModel: BeerListViewModel = BeerListViewModel(
        getBeers = getBeersInteractor,
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
    fun `getBeers method should set WithItems state if beers are fetched successfully`() {
        givenAnySuccessfullyResponseOnGetBeers()

        viewModel.getBeers()

        verify {
            mockStateObserver.onChanged(ofType(BeerListState.WithItems::class))
        }
    }

    @Test
    fun `getBeers method should set Loading state if beers are fetched from start`() {
        givenAnySuccessfullyResponseOnGetBeers()

        viewModel.getBeers(true)

        verify {
            mockStateObserver.onChanged(ofType(BeerListState.Loading::class))
        }
    }

    @Test
    fun `getBeers method should NOT set Loading state if beers are not fetched from start`() {
        viewModel.getBeers(false)

        verify(exactly = 0) {
            mockStateObserver.onChanged(ofType(BeerListState.Loading::class))
        }
    }

    @Test
    fun `getBeers method should set Error state if something went wrong`() {
        givenAnyFailureResponseOnGetBeers()

        viewModel.getBeers()

        verify {
            mockStateObserver.onChanged(ofType(BeerListState.Error::class))
        }
    }

    @Test
    fun `loadMore method should set Paging state if the state is WithItems`() {
        givenAnySuccessfullyResponseOnGetBeers()

        viewModel.getBeers()
        viewModel.loadMore()

        verify {
            mockStateObserver.onChanged(ofType(BeerListState.WithItems::class))
        }
    }

    private fun givenAnySuccessfullyResponseOnGetBeers() {
        val expectedBeers = listOf(mother.givenABeer())

        coEvery { getBeersInteractor(GetBeers.Params()) } returns expectedBeers.toRight()
    }

    private fun givenAnyFailureResponseOnGetBeers() {
        coEvery { getBeersInteractor(GetBeers.Params()) } returns
            Failure.ElementNotFound("No beers found")
                .toLeft()
    }
}
