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
class GetBeersTestclass {

    @Mock
    lateinit var beerRepository: BeerRepository

    lateinit var getBeersUseCase: GetBeers


    @Before
    fun setUp() {
        getBeersUseCase = GetBeers(beerRepository)
    }

    @Test
    fun `invocation always call the repository`() {
        runBlocking {
            getBeersUseCase(GetBeers.Params(false))

            verify(beerRepository, times(1)).getBeers(false)
        }
    }
}