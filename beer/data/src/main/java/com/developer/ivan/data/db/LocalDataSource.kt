package com.developer.ivan.data.db

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure

interface LocalDataSource {

    suspend fun getLocalBeers(): Either<Failure, List<Beer>>
    suspend fun getLocalBeer(id: Int): Either<Failure, Beer>

    suspend fun insertBeers(beers: List<Beer>)
    suspend fun updateBeer(beer: Beer): Beer
    suspend fun countBeers(): Int
}
