package com.developer.ivan.data.repositories

import com.developer.ivan.data.datasources.LocalDataSource
import com.developer.ivan.data.datasources.RemoteDataSource
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.testshared.beerList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BusRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var beerRepository: BeerRepository


    @Before
    fun setUp() {
        beerRepository = BeerRepositoryImplementation(localDataSource, remoteDataSource)

        runBlocking {
            whenever(localDataSource.countBeers()).thenReturn(beerList.size)
        }
    }

    @Test
    fun `getBeers always returns data from local data source if forceReload param its true`() {
        runBlocking {
            val forceParam = true
            val remoteData = Either.Right(beerList)
            val localData = flow { emit(remoteData) }

            whenever(localDataSource.getLocalBeers()).thenReturn(localData)
            whenever(remoteDataSource.getBeers(anyInt(), anyInt())).thenReturn(remoteData)

            beerRepository.getBeers(forceParam).collect {
                assertEquals(Either.Right(beerList), it)
            }
        }
    }

    @Test
    fun `getBeers always returns data from local data source if forceReload param its false`() {
        runBlocking {

            val forceParam = false
            val remoteData = Either.Right(beerList)
            val localData = flow { emit(remoteData) }

            whenever(localDataSource.getLocalBeers()).thenReturn(localData)
            whenever(remoteDataSource.getBeers(anyInt(), anyInt())).thenReturn(remoteData)

            beerRepository.getBeers(forceParam).collect {
                assertEquals(Either.Right(beerList), it)
            }
        }
    }

    @Test
    fun `getBeers insert remote data always on localdatasource`() {
        runBlocking {

            val remoteData = Either.Right(beerList)
            val localData = flow { emit(remoteData) }

            whenever(localDataSource.getLocalBeers()).thenReturn(localData)
            whenever(remoteDataSource.getBeers(anyInt(), anyInt())).thenReturn(remoteData)

            beerRepository.getBeers(false).collect {
                verify(remoteDataSource).getBeers(anyInt(), anyInt())
                verify(localDataSource).insertBeers(any())
            }


        }
    }

    @Test
    fun `getBeer obtain a local existing Beer `() {
        runBlocking {

            val localData = flow { emit(Either.Right(beerList[0])) }

            whenever(localDataSource.getLocalBeer(1)).thenReturn(localData)

            beerRepository.getBeer(1).collect {
                assertEquals(Either.Right(beerList[0]), it)
            }
        }
    }

    @Test
    fun `getBeer obtain a null result if Beer doesnt exists`() {
        runBlocking {

            val localData = flow { emit(Either.Left(Failure.NullResult)) }

            whenever(localDataSource.getLocalBeer(-1)).thenReturn(localData)

            beerRepository.getBeer(-1).collect {
                assertEquals(Either.Left(Failure.NullResult), it)
            }
        }
    }


}