package com.developer.ivan.beerapp.ui.main.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIBeer(
    val id: Int,
    val name: String,
    val tagline: String?,
    val description: String?,
    val image_url: String?,
    val abv: Double?,
    val ibu: Float?,
    val food_pairing: List<String>,
    val isAvailable: Boolean
) : Parcelable