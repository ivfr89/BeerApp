package com.developer.ivan.interactors

import com.developer.ivan.domain.*
import com.developer.ivan.repository.BeerRepository

class GetBeers(
    private val getBeerCount: GetBeerCount,
    private val beerRepository: BeerRepository
) :
    Interactor<Unit, List<Beer>>() {

    override suspend fun execute(params: Unit): Either<Failure, List<Beer>> =
        getBeerCount(Unit)
            .flatMap { count ->

                val value = count % Constants.Server.DEFAULT_SIZE
                val size = Constants.Server.DEFAULT_SIZE

                val page =
                    when {
                        count == 0 -> 0
                        value == 0 -> count / Constants.Server.DEFAULT_SIZE
                        else -> value
                    } + 1

                beerRepository.getBeers(page, size)
            }

}
