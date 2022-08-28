package com.developer.ivan.data.repositories

import com.developer.ivan.datasources.LocalDataSource
import com.developer.ivan.datasources.RemoteDataSource
import com.developer.ivan.domain.*
import com.developer.ivan.repository.BeerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class BeerRepositoryImplementation(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : BeerRepository {

    override suspend fun getBeerCount(): Either.Right<Int> =
        localDataSource.countBeers().toRight()

    override suspend fun getBeers(
        page: Int,
        size: Int
    ): Either<Failure, List<Beer>> {

        remoteDataSource.getBeers(page, size).flatMap { beers ->
            localDataSource.insertBeers(beers)
            Either.Right(beers)
        }

        return localDataSource.getLocalBeers()

    }

    override suspend fun getBeer(id: Int): Either<Failure, Beer> =
        localDataSource.getLocalBeer(id)

    override suspend fun updateBeer(beer: Beer): Either.Right<Unit> {
        localDataSource.updateBeer(beer)
        return Either.Right(Unit)
    }

}
