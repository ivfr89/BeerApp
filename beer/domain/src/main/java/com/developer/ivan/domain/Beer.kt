package com.developer.ivan.domain

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String?,
    val description: String?,
    val imageUrl: String?,
    val alcoholByVolume: Double?,
    val ibu: Float?,
    val foodPairing: List<String>,
    val isAvailable: Boolean
)
