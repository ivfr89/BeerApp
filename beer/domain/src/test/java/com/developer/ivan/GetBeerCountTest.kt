package com.developer.ivan

import com.developer.ivan.interactors.GetBeerCount
import com.developer.ivan.repository.BeerRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetBeerCountTest {
    private val beerRepository: BeerRepository = mockk(relaxed = true)
    private val getBeerCount = GetBeerCount(beerRepository)

    @Test
    fun `invoke should call beerRepository method`() {
        runBlocking {
            getBeerCount(Unit)
        }

        coVerify {
            beerRepository.getBeerCount()
        }
    }
}
