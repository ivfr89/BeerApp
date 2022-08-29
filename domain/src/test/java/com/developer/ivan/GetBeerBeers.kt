package com.developer.ivan

import com.developer.ivan.models.BeerMother
import com.developer.ivan.domain.toRight
import com.developer.ivan.interactors.GetBeerCount
import com.developer.ivan.interactors.GetBeers
import com.developer.ivan.repository.BeerRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetBeerBeers {
    private val beerRepository: BeerRepository = mockk(relaxed = true)
    private val getBeersCount : GetBeerCount = mockk(relaxed = true)
    private val getBeers = GetBeers(getBeersCount, beerRepository)
    private val mother: BeerMother = BeerMother()

    @Test
    fun `invoke should call getBeerCountInteractor and beerRepository method`() {

        coEvery { getBeersCount(Unit) } returns mother.givenAnyNumber().toRight()

        runBlocking {
            getBeers(GetBeers.Params())
        }

        coVerify {
            beerRepository.getBeers(any(), any(), any())
        }
    }
}
