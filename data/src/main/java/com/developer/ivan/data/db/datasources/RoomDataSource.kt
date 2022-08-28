package com.developer.ivan.data.db.datasources

import com.developer.ivan.data.db.DB
import com.developer.ivan.data.db.mapper.toDatabase
import com.developer.ivan.data.db.mapper.toDomain
import com.developer.ivan.datasources.LocalDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.toRight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(
    private val database: DB
) : LocalDataSource {

    object EmptyList : Failure.CustomFailure()

    override suspend fun getLocalBeers(): Either<Failure, List<Beer>> =
        withContext(Dispatchers.IO) {
            val beerList = database.beerDao().getAllBeers()
            if (beerList.isEmpty())
                Either.Left(EmptyList)
            else beerList.toDomain().toRight()
        }

    override suspend fun getLocalBeer(id: Int): Either<Failure, Beer> =
        withContext(Dispatchers.IO) {
            database.beerDao().getBeer(id)?.toDomain()?.toRight() ?: Either.Left(Failure.NullResult)
        }

    override suspend fun insertBeers(beers: List<Beer>) =
        withContext(Dispatchers.IO) {
            database.beerDao().insertBeers(
                beers.toDatabase()
            )
        }

    override suspend fun updateBeer(beer: Beer) = withContext(Dispatchers.IO) {
        database.beerDao().updateBeer(beer.toDatabase())
    }

    override suspend fun countBeers(): Int = withContext(Dispatchers.IO) {
        database.beerDao().getBeersCount()
    }
}
