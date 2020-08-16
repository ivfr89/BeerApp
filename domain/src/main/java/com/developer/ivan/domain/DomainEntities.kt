package com.developer.ivan.domain

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String?,
    val description: String?,
    val image_url: String?,
    val abv: Double?,
    val ibu: Float?,
    val food_pairing: List<String>,
    val isAvailable: Boolean)

sealed class Failure{

    abstract class CustomFailure : Failure()
    object NullResult : CustomFailure()
}
object NoParams
