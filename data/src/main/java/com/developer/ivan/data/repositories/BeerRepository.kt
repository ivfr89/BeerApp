package com.developer.ivan.data.repositories

import com.developer.ivan.data.datasources.LocalDataSource
import com.developer.ivan.data.datasources.RemoteDataSource
import com.developer.ivan.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

interface BeerRepository {

    suspend fun getBeers(
        force: Boolean = true
    ): Flow<Either<Failure, List<Beer>>>

    suspend fun getBeer(
        id: Int
    ): Flow<Either<Failure, Beer>>

    suspend fun updateBeer(
        beer: Beer
    ): Either.Right<Unit>
}

class BeerRepositoryImplementation(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : BeerRepository {

    override suspend fun getBeers(
        force: Boolean
    ): Flow<Either<Failure, List<Beer>>> {


        val count = localDataSource.countBeers()
        val value = count % Constants.Server.DEFAULT_SIZE
        val size = Constants.Server.DEFAULT_SIZE

        val page =
            when {
                count == 0 -> 0
                value == 0 -> count / Constants.Server.DEFAULT_SIZE
                else -> value
            } + 1

        return when (force) {
            true -> networkDataSourceSearchBeers(page, size)
            false -> localDataSource.getLocalBeers()
                .distinctUntilChanged()
                .flatMapLatest { result ->
                    networkDataSourceSearchBeers(page, size)
                }
        }
    }

    override suspend fun getBeer(id: Int): Flow<Either<Failure, Beer>> =
        localDataSource.getLocalBeer(id)

    override suspend fun updateBeer(beer: Beer): Either.Right<Unit> {
        localDataSource.updateBeer(beer)
        return Either.Right(Unit)
    }


    suspend fun networkDataSourceSearchBeers(
        page: Int,
        size: Int
    ): Flow<Either<Failure, List<Beer>>> {

        remoteDataSource.getBeers(page, size).flatMapToRight { beers ->
            localDataSource.insertBeers(beers)
            Either.Right(beers)
        }

        return localDataSource.getLocalBeers()

    }
}