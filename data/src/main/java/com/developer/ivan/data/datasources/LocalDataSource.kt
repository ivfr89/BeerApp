package com.developer.ivan.data.datasources

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

     suspend fun getLocalBeers(): Flow<Either<Failure, List<Beer>>>
     suspend fun getLocalBeer(id: Int): Flow<Either<Failure, Beer>>

     suspend fun insertBeers(beers: List<Beer>)
     suspend fun updateBeer(beer: Beer)
     suspend fun countBeers(): Int
}