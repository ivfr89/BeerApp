package com.developer.ivan.data.repositories

import com.developer.ivan.data.models.BeerMother
import com.developer.ivan.datasources.LocalDataSource
import com.developer.ivan.datasources.RemoteDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.toLeft
import com.developer.ivan.domain.toRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BeerRepositoryTest {

    private val mother: BeerMother = BeerMother()
    private val remoteDataSource = mockk<RemoteDataSource>(relaxed = true)
    private val localDataSource = mockk<LocalDataSource>(relaxed = true)
    private val repository = BeerDataRepository(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    @Test
    fun `getBeers always returns data from local data source if forceReload param is true`() {
        runBlocking {
            val expectedParam = true

            givenAnySuccessfullyResponseOnDataSources()

            repository.getBeers(expectedParam, mother.givenAnyNumber(), mother.givenAnyNumber())

            coVerify(exactly = 1) {
                localDataSource.getLocalBeers()
            }
        }
    }

    @Test
    fun `getBeers always returns data from local data source if forceReload param is false`() {
        runBlocking {
            val expectedParam = false

            givenAnySuccessfullyResponseOnDataSources()

            repository.getBeers(expectedParam, mother.givenAnyNumber(), mother.givenAnyNumber())

            coVerify(exactly = 1) {
                localDataSource.getLocalBeers()
            }

            coVerify(inverse = true) {
                remoteDataSource.getBeers(any(), any())
            }
        }
    }

    @Test
    fun `getBeers inserts remote data always on localdatasource`() {
        runBlocking {
            val expectedParam = true
            val expectedInsertedData = listOf(mother.givenABeer())

            givenAnySuccessfullyResponseOnDataSources()

            repository.getBeers(expectedParam, mother.givenAnyNumber(), mother.givenAnyNumber())

            coVerifyOrder {
                remoteDataSource.getBeers(any(), any())
                localDataSource.insertBeers(expectedInsertedData)
            }
        }
    }

    @Test
    fun `getBeer obtain a local existing Beer`() {
        runBlocking {
            val expectedId = mother.givenAnyId()
            val expectedBeer = mother.givenABeer(id = expectedId)

            coEvery { localDataSource.getLocalBeer(expectedId) } returns expectedBeer.toRight()

            val response = repository.getBeer(expectedId)
            assert(response.exists { it == expectedBeer })
        }
    }

    @Test
    fun `getBeer obtain a null result if Beer doesn't exists`() {
        runBlocking {
            val expectedId = mother.givenAnyId()

            coEvery { localDataSource.getLocalBeer(expectedId) } returns Failure.NullResult.toLeft()

            val response = repository.getBeer(expectedId)
            assert(response.swap().exists { it is Failure.NullResult })
        }
    }

    private fun givenAnySuccessfullyResponseOnDataSources(localData: List<Beer> = listOf(mother.givenABeer())) {
        coEvery { localDataSource.getLocalBeers() } returns localData.toRight()
        coEvery { remoteDataSource.getBeers(any(), any()) } returns localData.toRight()
    }
}
