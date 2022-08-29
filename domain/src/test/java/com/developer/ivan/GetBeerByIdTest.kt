package com.developer.ivan

import com.developer.ivan.interactors.GetBeerById
import com.developer.ivan.models.BeerMother
import com.developer.ivan.repository.BeerRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetBeerByIdTest {
    private val beerRepository: BeerRepository = mockk(relaxed = true)
    private val getBeerById = GetBeerById(beerRepository)
    private val mother = BeerMother()

    @Test
    fun `invoke should call beerRepository method`() {
        val expectedBeerId = mother.givenAnyid()

        runBlocking {
            getBeerById(GetBeerById.Params(expectedBeerId))
        }

        coVerify {
            beerRepository.getBeer(expectedBeerId)
        }
    }
}
