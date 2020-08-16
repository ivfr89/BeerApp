package com.developer.ivan.usecases

import com.developer.ivan.data.repositories.BeerRepository
import com.developer.ivan.domain.*
import kotlinx.coroutines.flow.Flow

class UpdateBeer(private val beerRepository: BeerRepository) :
    UseCase<UpdateBeer.Params, Unit>() {

    override suspend fun execute(params: Params): Flow<Either<Failure, Unit>> =
        beerRepository.updateBeer(params.beer).asFlow()


    class Params(val beer: Beer)

}
