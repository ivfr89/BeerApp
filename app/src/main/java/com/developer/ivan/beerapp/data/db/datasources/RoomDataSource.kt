package com.developer.ivan.beerapp.data.db.datasources

import com.developer.ivan.beerapp.data.db.*
import com.developer.ivan.beerapp.data.db.mapper.DBMapper
import com.developer.ivan.data.datasources.LocalDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class RoomDataSource(
    private val dbMapper: DBMapper,
    database: DB
) : LocalDataSource {

    private val beerDao: BeerDao = database.beerDao()

    object EmptyList : Failure.CustomFailure()

    override suspend fun getLocalBeers(): Flow<Either<Failure, List<Beer>>> =
        withContext(Dispatchers.IO) {
            beerDao.getAllBeers()
                .distinctUntilChanged()
                .mapLatest { list ->
                    if (list.isEmpty())
                        Either.Left(EmptyList)
                    else Either.Right(
                        dbMapper.convertDBBeersToDomain(
                            list
                        )
                    )
                }
        }

    override suspend fun getLocalBeer(id: Int): Flow<Either<Failure, Beer>> =
        withContext(Dispatchers.IO) {
            beerDao.getBeer(id)
                .distinctUntilChanged()
                .mapLatest { item ->
                    if (item == null)
                        Either.Left(Failure.NullResult)
                    else
                        Either.Right(
                            dbMapper.convertDBBeerToDomain(
                                item
                            )
                        )
                }
        }

    override suspend fun insertBeers(beers: List<Beer>) =
        withContext(Dispatchers.IO) {
            beerDao.insertBeers(
                dbMapper.convertBeersToDBBeers(beers)
            )
        }

    override suspend fun updateBeer(beer: Beer) = withContext(Dispatchers.IO) {
        beerDao.updateBeer(dbMapper.convertBeerToDBBeer(beer))
    }

    override suspend fun countBeers(): Int = withContext(Dispatchers.IO) {
        beerDao.getBeersCount()
    }
}