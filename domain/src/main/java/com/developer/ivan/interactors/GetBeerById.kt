package com.developer.ivan.interactors

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.repository.BeerRepository

class GetBeerById(private val beerRepository: BeerRepository) :
    Interactor<GetBeerById.Params, Beer>() {

    override suspend fun execute(params: Params): Either<Failure, Beer> =
        beerRepository.getBeer(params.id)

    class Params(val id: Int)
}
