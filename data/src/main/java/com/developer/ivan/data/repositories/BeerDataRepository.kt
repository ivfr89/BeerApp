package com.developer.ivan.data.repositories

import com.developer.ivan.data.db.LocalDataSource
import com.developer.ivan.data.server.datasources.RemoteDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.flatMap
import com.developer.ivan.domain.toRight
import com.developer.ivan.repository.BeerRepository

class BeerDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : BeerRepository {

    override suspend fun getBeerCount(): Either.Right<Int> =
        localDataSource.countBeers().toRight()

    override suspend fun getBeers(
        force: Boolean,
        page: Int,
        size: Int
    ): Either<Failure, List<Beer>> =
        when (force) {
            true -> {
                remoteDataSource.getBeers(page, size).flatMap { beers ->
                    localDataSource.insertBeers(beers)
                    Either.Right(beers)
                }
                localDataSource.getLocalBeers()
            }
            false -> {
                localDataSource.getLocalBeers()
            }
        }

    override suspend fun getBeer(id: Int): Either<Failure, Beer> =
        localDataSource.getLocalBeer(id)

    override suspend fun updateBeer(beer: Beer): Either.Right<Beer> {
        return localDataSource.updateBeer(beer).toRight()
    }
}
