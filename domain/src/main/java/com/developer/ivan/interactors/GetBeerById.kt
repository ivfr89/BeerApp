package com.developer.ivan.interactors

import com.developer.ivan.domain.*
import com.developer.ivan.repository.BeerRepository
import kotlinx.coroutines.flow.Flow

class GetBeerById(private val beerRepository: BeerRepository) :
    Interactor<GetBeerById.Params, Beer>() {

    override suspend fun execute(params: Params): Either<Failure, Beer> =
        beerRepository.getBeer(params.id)


    class Params(val id: Int)

}
