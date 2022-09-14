package com.developer.ivan.interactors

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Constants
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.flatMap
import com.developer.ivan.repository.BeerRepository

class GetBeers(
    private val getBeerCount: GetBeerCount,
    private val beerRepository: BeerRepository
) :
    Interactor<GetBeers.Params, List<Beer>> {

    override suspend fun execute(params: Params): Either<Failure, List<Beer>> =
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

                beerRepository.getBeers(force = params.forceReload, page, size)
            }

    data class Params(
        val forceReload: Boolean = true
    )
}
