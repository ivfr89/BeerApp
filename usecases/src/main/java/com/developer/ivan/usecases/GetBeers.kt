package com.developer.ivan.usecases

import com.developer.ivan.data.repositories.BeerRepository
import com.developer.ivan.domain.*
import kotlinx.coroutines.flow.Flow

class GetBeers(private val beerRepository: BeerRepository) :
    UseCase<GetBeers.Params, List<Beer>>() {

    override suspend fun execute(params: Params): Flow<Either<Failure, List<Beer>>> =
        beerRepository.getBeers(params.force)

    class Params(val force: Boolean = false)

}