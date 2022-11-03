package com.developer.ivan.beerapp.fake

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.toLeft
import com.developer.ivan.domain.toRight
import com.developer.ivan.repository.BeerRepository

var beerFakeList = (0..3).map {
    Beer(
        id = it,
        name = "Beer $it",
        tagline = "Beer $it",
        description = "Description for beer $it",
        imageUrl = "http://fakebeer.com/img1.jpg",
        alcoholByVolume = 0.0,
        ibu = 0f,
        foodPairing = emptyList(),
        isAvailable = true
    )
}

class BeerFakeDataRepository : BeerRepository {

    override suspend fun getBeerCount(): Either.Right<Int> =
        beerFakeList.count().toRight()

    override suspend fun getBeers(
        force: Boolean,
        page: Int,
        size: Int
    ): Either<Failure, List<Beer>> =
        beerFakeList.toRight()

    override suspend fun getBeer(id: Int): Either<Failure, Beer> =
        beerFakeList.find { it.id == id }?.toRight() ?: Failure.NullResult.toLeft()

    override suspend fun updateBeer(beer: Beer): Either.Right<Beer> {
        beerFakeList = beerFakeList.map { if (it.id == beer.id) beer else it }
        return Either.Right(beer)
    }
}
