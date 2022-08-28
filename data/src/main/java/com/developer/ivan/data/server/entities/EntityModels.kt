package com.developer.ivan.data.server.entities

data class BeerApiModel(
    val abv: Double?,
    val attenuation_level: Float?,
    val boil_volume: BoilVolumeApiModel?,
    val brewers_tips: String?,
    val contributed_by: String,
    val description: String?,
    val ebc: Float?,
    val first_brewed: String?,
    val food_pairing: List<String>,
    val ibu: Float?,
    val id: Int,
    val image_url: String?,
    val ingredients: IngredientsApiModel?,
    val method: MethodApiModel?,
    val name: String,
    val ph: Double?,
    val srm: Float?,
    val tagline: String?,
    val target_fg: Float?,
    val target_og: Float?,
    val volume: AmountApiModel?
)

data class BoilVolumeApiModel(
    val unit: String?,
    val value: Float?
)

data class IngredientsApiModel(
    val hops: List<HopApiModel>,
    val malt: List<MaltModel>,
    val yeast: String?
)

data class MethodApiModel(
    val fermentation: FermentationApiModel?,
    val mash_temp: List<MashTempApiModel>,
    val twist: String?
)

data class HopApiModel(
    val add: String?,
    val amount: AmountApiModel?,
    val attribute: String?,
    val name: String?
)

data class MaltModel(
    val amount: AmountApiModel?,
    val name: String?
)

data class AmountApiModel(
    val unit: String?,
    val value: Float?
)

data class FermentationApiModel(
    val temp: AmountApiModel?
)

data class MashTempApiModel(
    val duration: Float?,
    val temp: AmountApiModel?
)
