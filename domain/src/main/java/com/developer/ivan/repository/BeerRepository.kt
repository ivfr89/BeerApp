package com.developer.ivan.repository

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure

interface BeerRepository {

    suspend fun getBeerCount(): Either.Right<Int>

    suspend fun getBeers(
        force: Boolean,
        page: Int,
        size: Int
    ): Either<Failure, List<Beer>>

    suspend fun getBeer(
        id: Int
    ): Either<Failure, Beer>

    suspend fun updateBeer(
        beer: Beer
    ): Either.Right<Unit>
}
