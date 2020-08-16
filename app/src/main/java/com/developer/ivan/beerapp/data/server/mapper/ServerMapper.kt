package com.developer.ivan.beerapp.data.server.mapper

import com.developer.ivan.beerapp.data.server.entities.EntityBeer
import com.developer.ivan.domain.*


class ServerMapper {

    fun convertEntitiesBeersToDomain(entityArticles: List<EntityBeer>): Either<Failure, List<Beer>> =
        Either.Right(entityArticles.mapNotNull { entity ->
            when (val result = convertEntityBeerToDomain(entity)) {
                is Either.Right -> result.b
                else -> null
            }
        })

    fun convertEntityBeerToDomain(entityArticle: EntityBeer): Either<Failure, Beer> =
        Either.Right(with(entityArticle) {
            Beer(
                id = id,
                name = name,
                tagline = tagline,
                description = description,
                image_url = image_url,
                abv = abv,
                ibu = ibu,
                food_pairing = food_pairing,
                isAvailable = true
            )
        })


}