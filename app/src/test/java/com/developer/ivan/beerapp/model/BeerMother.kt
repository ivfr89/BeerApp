package com.developer.ivan.beerapp.model

import com.developer.ivan.domain.Beer

class BeerMother {

    companion object {
        private const val anyId: Int = 0
        private const val anyName: String = "anyName"
        private const val anyTag: String = "anyTag"
        private const val anyDescription: String = "anyDescription"
        private const val anyUrl: String = "anyUrl"
        private const val anyAlcoholByVolume: Double = 0.0
        private const val anyIbu: Float = 0f
        private const val anyAvailability: Boolean = true
        private val anyFoodPairing: List<String> = emptyList()
    }

    fun givenAnyid() = anyId

    fun givenABeer(
        id: Int = anyId,
        name: String = anyName,
        tagline: String? = anyTag,
        description: String? = anyDescription,
        imageUrl: String? = anyUrl,
        alcoholByVolume: Double? = anyAlcoholByVolume,
        ibu: Float? = anyIbu,
        foodPairing: List<String> = anyFoodPairing,
        isAvailable: Boolean = anyAvailability
    ): Beer = Beer(
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
}
