package com.developer.ivan.interactors

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.repository.BeerRepository

class UpdateBeer(private val beerRepository: BeerRepository) :
    Interactor<UpdateBeer.Params, Beer> {

    override suspend fun execute(params: Params): Either<Failure, Beer> =
        beerRepository.updateBeer(params.beer)

    class Params(val beer: Beer)
}
