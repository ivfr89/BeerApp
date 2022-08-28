package com.developer.ivan.data.db.mapper

import com.developer.ivan.data.db.BeerDb
import com.developer.ivan.domain.Beer

fun List<BeerDb>.toDomain(): List<Beer> =
    map { entity ->
        entity.toDomain()
    }

fun BeerDb.toDomain(): Beer =
    Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl,
        alcoholByVolume = alcoholByVolume,
        ibu = ibu,
        foodPairing = foodPairing,
        isAvailable = isAvailable
    )


fun List<Beer>.toDatabase() =
    map { beer ->
        beer.toDatabase()
    }


fun Beer.toDatabase() =
        BeerDb(
            id = id,
            name = name,
            tagline = tagline,
            description = description,
            imageUrl = imageUrl,
            alcoholByVolume = alcoholByVolume,
            ibu = ibu,
            foodPairing = foodPairing,
            isAvailable = isAvailable
        )
