package com.developer.ivan.beerapp.data.server.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityBeer(
    val abv: Double?,
    val attenuation_level: Float?,
    val boil_volume: EntityBoilVolume?,
    val brewers_tips: String?,
    val contributed_by: String,
    val description: String?,
    val ebc: Float?,
    val first_brewed: String?,
    val food_pairing: List<String>,
    val ibu: Float?,
    val id: Int,
    val image_url: String?,
    val ingredients: EntityIngredients?,
    val method: EntityMethod?,
    val name: String,
    val ph: Double?,
    val srm: Float?,
    val tagline: String?,
    val target_fg: Float?,
    val target_og: Float?,
    val volume: EntityAmount?
)

@JsonClass(generateAdapter = true)
data class EntityBoilVolume(
    val unit: String?,
    val value: Float?
)

@JsonClass(generateAdapter = true)
data class EntityIngredients(
    val hops: List<EntityHop>,
    val malt: List<EntityMalt>,
    val yeast: String?
)

@JsonClass(generateAdapter = true)
data class EntityMethod(
    val fermentation: EntityFermentation?,
    val mash_temp: List<EntityMashTemp>,
    val twist: String?
)

@JsonClass(generateAdapter = true)
data class EntityHop(
    val add: String?,
    val amount: EntityAmount?,
    val attribute: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class EntityMalt(
    val amount: EntityAmount?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class EntityAmount(
    val unit: String?,
    val value: Float?
)

@JsonClass(generateAdapter = true)
data class EntityFermentation(
    val temp: EntityAmount?
)

@JsonClass(generateAdapter = true)
data class EntityMashTemp(
    val duration: Float?,
    val temp: EntityAmount?
)