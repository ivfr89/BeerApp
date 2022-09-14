package com.developer.ivan.interactors

import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.repository.BeerRepository

class GetBeerCount(private val beerRepository: BeerRepository) :
    Interactor<Unit, Int> {

    override suspend fun execute(params: Unit): Either<Failure, Int> =
        beerRepository.getBeerCount()
}
