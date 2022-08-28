package com.developer.ivan.beerapp.ui.main.fragments

import android.os.Parcelable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.beerapp.ui.mapper.UIMapper
import com.developer.ivan.beerapp.utils.CoroutinesMainDispatcherRule
import com.developer.ivan.domain.Either
import com.developer.ivan.interactors.GetBeers
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.refEq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BeerListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutinesMainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var parcelizeState: Parcelable

    @Mock
    lateinit var getBeers: GetBeers

    @Mock
    lateinit var uiMapper: UIMapper

    private lateinit var mViewModel: BeerListViewModel

    @Mock
    lateinit var observer: Observer<BeerListViewModel.BeerListState>

    @Before
    fun setupViewModel() {
        mViewModel =
            BeerListViewModel(getBeers, uiMapper, parcelizeState, mainCoroutineRule.testDispatcher)
    }


    @Test
    fun `getBeers change state to IsLoading when is called`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            mViewModel.beersStateListData.observeForever(observer)

            val mainFlow = flow {
                emit(Either.Right(beerList))
            }

            whenever(getBeers.invoke(any())).thenReturn(mainFlow)

            mViewModel.getBeers()

            verify(observer).onChanged(refEq(BeerListViewModel.BeerListState.IsLoading(true)))

        }

    @Test
    fun `getBeers change state to ShowItems(happy path flow) when retrieves data`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            mViewModel.beersStateListData.observeForever(observer)

            val expected = listOf(
                BeerUi(
                    1,
                    "First beer",
                    "mytag",
                    "Description",
                    "https://www.history.com/.image/t_share/MTU4NTE1Nzg2MDcwMTA3Mzk0/beer-oldest.jpg",
                    1.0,
                    5f,
                    listOf("Pizza, Nuggets"),
                    true
                )
                ,
                BeerUi(
                    2,
                    "Second beer",
                    "mytag",
                    "Description",
                    "https://www.history.com/.image/t_share/MTU4NTE1Nzg2MDcwMTA3Mzk0/beer-oldest.jpg",
                    2.0,
                    10f,
                    listOf("Apple, Nuggets"),
                    true
                )
            )


            val mainFlow = flow {
                emit(Either.Right(beerList))
            }

            whenever(getBeers.invoke(any())).thenReturn(mainFlow)
            whenever(uiMapper.convertBeersToUIBeers(any())).thenReturn(expected)

            mViewModel.getBeers()

            verify(observer).onChanged(refEq(BeerListViewModel.BeerListState.ShowItems(expected)))

        }


}