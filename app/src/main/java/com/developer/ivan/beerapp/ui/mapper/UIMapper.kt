package com.developer.ivan.beerapp.ui.mapper

import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.domain.Beer

fun BeerUi.toDomain(): Beer =
    Beer(
        id = id.toInt(),
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl,
        alcoholByVolume = alcoholByVolume,
        ibu = ibu,
        foodPairing = foodPairing,
        isAvailable = isAvailable
    )

fun List<Beer>.toUi() =
    map { beer ->
        beer.toUi()
    }


fun Beer.toUi() =
        BeerUi(
            id = id.toString(),
            name = name,
            tagline = tagline,
            description = description,
            imageUrl = imageUrl,
            alcoholByVolume = alcoholByVolume,
            ibu = ibu,
            foodPairing = foodPairing,
            isAvailable = isAvailable
        )
