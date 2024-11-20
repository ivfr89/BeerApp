@file:Suppress("ktlint:filename")

package com.developer.ivan.data.server.entities

import com.developer.ivan.domain.Beer

fun BeerApiModel.toDomain() =
    Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = image_url,
        alcoholByVolume = abv,
        ibu = ibu,
        foodPairing = food_pairing,
        isAvailable = true
    )

fun List<BeerApiModel>.toDomain() =
    map { it.toDomain() }
