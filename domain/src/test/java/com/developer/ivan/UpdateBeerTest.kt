package com.developer.ivan

import com.developer.ivan.interactors.UpdateBeer
import com.developer.ivan.models.BeerMother
import com.developer.ivan.repository.BeerRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateBeerTest {
    private val beerRepository: BeerRepository = mockk(relaxed = true)
    private val updateBeer = UpdateBeer(beerRepository)
    private val mother = BeerMother()

    @Test
    fun `invoke should call beerRepository method`() {

        val expectedBeer = mother.givenABeer()

        runBlocking {
            updateBeer(UpdateBeer.Params(expectedBeer))
        }

        coVerify {
            beerRepository.updateBeer(expectedBeer)
        }
    }
}
