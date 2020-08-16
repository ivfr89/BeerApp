package com.developer.ivan.usecases

import com.developer.ivan.data.repositories.BeerRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBeerByIdTestclass {

    @Mock
    lateinit var beerRepository: BeerRepository

    lateinit var getBeerUseCase: GetBeerById


    @Before
    fun setUp() {
        getBeerUseCase = GetBeerById(beerRepository)
    }

    @Test
    fun `invocation always call the repository`() {
        runBlocking {
            getBeerUseCase(GetBeerById.Params(-1))

            verify(beerRepository, times(1)).getBeer(-1)
        }
    }
}